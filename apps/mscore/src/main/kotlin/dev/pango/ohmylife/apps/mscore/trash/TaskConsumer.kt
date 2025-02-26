package dev.pango.ohmylife.apps.mscore.trash

import dev.pango.ohmylife.apps.mscore.application.CategorizedTaskInfoDTO
import dev.pango.ohmylife.apps.mscore.application.RabbitMessageDTO
import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.factory
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

//class TaskConsumer(private val taskRepository: TaskRepository) {
//
//
//
//    fun startListening() {
//        println("start listening")
//        factory.newConnection().use { connection ->
//            connection.createChannel().use { channel ->
//                channel.queueDeclare("task_categorized", true, false, false, null)
//                val deliverCallback = { _: String, delivery: com.rabbitmq.client.Delivery ->
//                    println("Received message: ${String(delivery.body)}")
//                    val message = String(delivery.body)
//                    val event = Json.decodeFromString<Map<String, Any>>(message)
//
//                    val taskId = event["taskId"] as String
//                    val theme = event["theme"] as String
//                    val experience = (event["experience"] as Double).toInt()
//                    val price = (event["price"] as Double).toInt()
//
//                    val task = taskRepository.getTask(taskId)
//                    task.onRight {
//                        val taskUpdated= it.copy(
//                            theme = TaskDomain.Theme.valueOf( theme),
//                            experience = experience,
//                            price = price
//                        )
//
//                        taskRepository.updateTask(taskUpdated)
//                    }
//                    Unit
//                }
//                channel.basicConsume("task_categorized", true, deliverCallback, { _ -> })
//            }
//        }
//    }
//}
class TaskConsumer(private val taskRepository: TaskRepository) {


    fun startListening() {
        println("TaskConsumer: Iniciando escucha en la cola 'task_categorized'...")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = factory.newConnection()
                val channel = connection.createChannel()
                channel.queueDeclare("task_categorized", true, false, false, null)

                val deliverCallback = { _: String, delivery: com.rabbitmq.client.Delivery ->
                    val message = String(delivery.body)
                    println("TaskConsumer: Mensaje recibido -> $message")

                    try {
                        val event = Json.decodeFromString<RabbitMessageDTO<CategorizedTaskInfoDTO>>(message)

                        val taskId = event.data.taskId
                        val theme = event.data.theme
                        val experience = event.data.experience
                        val price = event.data.price

                        val task = taskRepository.getTask(taskId)
                        task.onRight {
//                            val taskUpdated = it.copy(
//                                theme = TaskDomain.Theme.valueOf(theme),
//                                experience = experience,
//                                price = price
//                            )
//                            taskRepository.updateTask(taskUpdated)
                        }
                        Unit
                    } catch (e: Exception) {
                        println("TaskConsumer: Error procesando mensaje - ${e.localizedMessage}")
                    }
                }

                // Mantiene el consumidor escuchando
                channel.basicConsume("task_categorized", true, deliverCallback, { _ -> })

            } catch (e: Exception) {
                println("TaskConsumer: Error al conectar con RabbitMQ - ${e.localizedMessage}")
            }
        }
    }
}