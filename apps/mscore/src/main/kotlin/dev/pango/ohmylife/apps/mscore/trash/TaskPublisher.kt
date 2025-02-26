package dev.pango.ohmylife.apps.mscore.trash


import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.factory
import kotlinx.serialization.json.Json

class TaskPublisher {


    fun publishTaskCreated(taskId: String, title: String, description: String?) {
        try {
            factory.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    val message = Json.encodeToString(
                        mapOf("taskId" to taskId, "title" to title, "description" to description)
                    )

                    channel.queueDeclare("task_created", true, false, false, null)
                    channel.basicPublish("", "task_created", null, message.toByteArray())
                }
            }
        } catch (e: Exception) {
            println("Error publishing task: ${e.message}")
            e.printStackTrace()
        }
    }
}