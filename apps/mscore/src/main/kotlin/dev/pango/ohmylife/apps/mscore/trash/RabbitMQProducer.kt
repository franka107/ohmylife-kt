package dev.pango.ohmylife.apps.mscore.trash


import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.factory

object RabbitMQProducer {
    private const val QUEUE_NAME = "task_queue"

    fun sendMessage(message: String) {


        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.queueDeclare(QUEUE_NAME, true, false, false, null)
                channel.basicPublish("", QUEUE_NAME, null, message.toByteArray())
                println("📤 Mensaje enviado: $message")
            }
        }
    }
}