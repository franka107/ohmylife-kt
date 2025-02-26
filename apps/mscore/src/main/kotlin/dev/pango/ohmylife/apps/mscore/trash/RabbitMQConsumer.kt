package dev.pango.ohmylife.apps.mscore.trash


import com.rabbitmq.client.*
import dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq.factory

object RabbitMQConsumer {
    private const val QUEUE_NAME = "task_queue"

    fun startListening() {
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare(QUEUE_NAME, true, false, false, null)
        println("📥 Esperando mensajes en la cola '$QUEUE_NAME'...")

        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                val message = body?.toString(Charsets.UTF_8) ?: ""
                println("✅ Mensaje recibido: $message")
            }
        }

        channel.basicConsume(QUEUE_NAME, true, consumer)
    }
}