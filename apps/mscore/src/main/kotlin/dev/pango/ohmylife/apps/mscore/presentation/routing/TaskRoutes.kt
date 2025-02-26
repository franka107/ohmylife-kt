package dev.pango.ohmylife.apps.mscore.presentation.routing

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import dev.pango.ohmylife.apps.mscore.application.service.TaskService
import dev.pango.ohmylife.features.sharedkernel.mapper.toDto
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import org.koin.ktor.ext.inject
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.taskRoutes() {
    val taskService by inject<TaskService>()
    val taskRepository by inject<TaskRepository>()

    routing {
        // Listar tareas
        get("/tasks") {
            val result = taskRepository.getTaskList()
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(it.toDto()) }
            )
        }

        // Crear tarea
        post("/tasks") {
            val request = call.receive<TaskDto>()
            val result = taskService.createTask(request)
            result.fold(
                ifLeft = { call.respond(mapOf("error" to it.message, "cause" to it.cause?.message)) },
                ifRight = { call.respond(mapOf("message" to "Task created successfully")) }
            )
        }
    }
}