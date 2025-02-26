package dev.pango.ohmylife.apps.mscore.application.service

import arrow.core.Either
import arrow.core.raise.either
import dev.pango.ohmylife.apps.mscore.application.dto.TaskCategoryDTO
import dev.pango.ohmylife.apps.mscore.application.dto.TaskDifficultyDTO
import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.IaClient
import dev.pango.ohmylife.features.sharedkernel.mapper.toDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority
import dev.pango.ohmylife.features.sharedkernel.domain.failure.AppFailure
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import kotlinx.serialization.json.Json
import kotlin.math.pow

class TaskService (
    private val taskRepository: TaskRepository,
    private val iaClient: IaClient
) {

    suspend fun createTask(taskDto: TaskDto): Either<AppFailure, String> = either {
        val task: TaskDomain = taskDto.toDomain()
        val taskDifficulty = processTaskDifficulty(task.title, task.description, task.priority).bind()
        val taskCategory = processTaskCategory(task.title, task.description).bind()
        val taskExperiencePoints = calculateExperiencePoints(taskDifficulty.difficultyPoints)
        val taskRewardMoney = calculateRewardMoney(taskDifficulty.difficultyPoints)
        val taskUpdated = task.copy(
            difficultyPoints = taskDifficulty.difficultyPoints,
            difficultyReason = taskDifficulty.difficultyReason,
            experiencePoints = taskExperiencePoints,
            rewardMoney = taskRewardMoney,
            categoryType = TaskCategoryType.valueOf(taskCategory.categoryType),
            categoryReason = taskCategory.categoryReason
        )
        val result = taskRepository.createTask(taskUpdated).bind()
        result
    }

    suspend fun playTask(taskId: String): Either<AppFailure, Unit> = either {
        val task = taskRepository.getTask(taskId).bind()
        task.start()
        taskRepository.updateTask(task).bind()
    }

    suspend fun pauseTask(taskId: String): Either<AppFailure, Unit> = either {
        val task = taskRepository.getTask(taskId).bind()
        task.pause()
        taskRepository.updateTask(task).bind()
    }

    suspend fun stopTask(taskId: String): Either<AppFailure, Unit> = either {
        val task = taskRepository.getTask(taskId).bind()
        task.stop()
        taskRepository.updateTask(task).bind()
    }

    private suspend  fun processTaskDifficulty(
        taskTitle: String,
        taskDescription: String?,
        taskPriority: TaskPriority
    ): Either<AppFailure, TaskDifficultyDTO> = Either.catch {
        val prompt = """
            Clasifica la dificultad de la siguiente tarea en una escala del 1 al 100, considerando también su prioridad:

            - CRITICAL (🔥): Debe completarse de inmediato, con consecuencias graves si se ignora.
            - HIGH (⚡): Muy importante pero no siempre urgente; contribuye significativamente al progreso.
            - MEDIUM (⏳): Moderadamente importante y urgente; impacta a largo plazo pero no es esencial a corto plazo.
            - LOW (🌀): Poca urgencia e impacto; puede hacerse en cualquier momento sin consecuencias relevantes.
            - OPTIONAL (🎯): No tiene urgencia ni impacto real, pero puede hacerse por diversión o recompensa extra.

            Asigna la dificultad en una escala del 1 al 100, donde:
            - 1-10: Tareas triviales, rápidas y sin esfuerzo.
            - 11-30: Tareas simples que requieren algo de atención pero son rutinarias.
            - 31-50: Tareas moderadas que toman tiempo pero son manejables con esfuerzo constante.
            - 51-70: Tareas desafiantes que requieren habilidades, concentración y tiempo considerable.
            - 71-90: Tareas avanzadas que demandan alta especialización y esfuerzo prolongado.
            - 91-100: Tareas extremadamente difíciles, raras o innovadoras.

            Evalúa la dificultad en función de los siguientes criterios:
            - Esfuerzo requerido: cuánta energía y concentración necesita el usuario.
            - Tiempo estimado: cuánto tiempo tomará completarla.
            - Conocimientos y habilidades: qué tan avanzada debe ser la experiencia del usuario.
            - Claridad y estructura: tareas mal definidas son más difíciles porque requieren planificación adicional.
            - Recursos necesarios: si se requieren herramientas especializadas o asistencia externa.
            - Prioridad: Tareas de mayor prioridad tienden a ser más exigentes en términos de urgencia y ejecución.

            Devuelve la respuesta en formato JSON puro, sin agregar comentarios ni texto adicional.
            **No devuelvas la respuesta dentro de un bloque de código ni con triple tilde invertida.**
            {
              "difficultyPoints": <número del 1 al 100>,
              "difficultyReason": "<explicación clara y concisa de por qué se asignó esta dificultad>"
            }

            Título de la tarea: ${taskTitle}
            Descripción de la tarea (opcional): ${taskDescription ?: "No especificada"}
            Prioridad de la tarea: ${taskPriority}
        """.trimIndent()



        val taskDifficultyRaw = iaClient.sendAndWait(prompt)
        println("sendAndWaitFinished")
        val taskDifficultyDTO: TaskDifficultyDTO = Json.decodeFromString(taskDifficultyRaw)
        taskDifficultyDTO
    }.mapLeft {
        AppFailure.UnknownFailure(it)
    }

    private suspend  fun processTaskCategory(title: String, description: String?): Either<AppFailure, TaskCategoryDTO> =Either.catch {
        val prompt = """
            Clasifica la siguiente tarea en una de las siguientes categorías:
            
            - KNOWLEDGE: Aprendizaje, estudio o mejora de habilidades intelectuales.
            - WORK: Responsabilidades laborales, ejecución de proyectos o productividad.
            - CREATIVITY: Arte, diseño, música, escritura o innovación.
            - PHYSICAL: Ejercicio, salud y bienestar físico.
            - SOCIAL: Amistades, networking o ayuda a la comunidad.
            - DISCIPLINE: Hábitos, planificación, organización y autodisciplina.
            - FINANCE: Gestión de dinero, presupuesto e inversiones.
            - LEISURE: Entretenimiento, relajación y recreación.
            
            Evalúa la categoría considerando los siguientes criterios:
            - Propósito principal: ¿Cuál es el objetivo principal de la tarea?
            - Actividad involucrada: ¿Qué tipo de acción requiere la tarea?
            - Beneficio esperado: ¿Cómo impacta al usuario en términos de desarrollo personal, laboral o bienestar?
            
             Devuelve la respuesta en formato JSON puro, sin agregar comentarios ni texto adicional.
            **No devuelvas la respuesta dentro de un bloque de código ni con triple tilde invertida.**
            
            {
              "categoryType": "<nombre de la categoría>",
              "categoryReason": "<explicación clara y concisa de por qué se eligió esta categoría>"
            }

            Título de la tarea: ${title}
            Descripción de la tarea (opcional): ${description ?: "No especificada"}
        """.trimIndent()


        val taskCategoryRaw = iaClient.sendAndWait(prompt)
        val taskCategoryDTO: TaskCategoryDTO = Json.decodeFromString(taskCategoryRaw)
        taskCategoryDTO
    }.mapLeft {
        AppFailure.UnknownFailure(it)
    }

    private fun calculateExperiencePoints(difficulty: Int): Int {
        require(difficulty in 1..100) { "La dificultad debe estar entre 1 y 100" }
        return (difficulty * difficulty / 10.0 + 5).toInt()
    }

    private fun calculateRewardMoney(difficulty: Int): Int {
        require(difficulty in 1..100) { "La dificultad debe estar entre 1 y 100" }
        return (difficulty.toDouble().pow(3) / 100 + 10).toInt()
    }
}