package dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection

enum class RabbitMQQueue(val queueName: String) {
    TASK_QUEUE("task_queue"),
    IA_QUEUE("ia_queue");
}


class RabbitMQProvider {
    private val connection: Connection by lazy { factory.newConnection() }
    private val channel: Channel by lazy {
        connection.createChannel().apply {
            RabbitMQQueue.entries.forEach { queue ->
                queueDeclare(queue.queueName, true, false, false, null)
            }
        }
    }

    fun obtainChannel(): Channel = channel
}

