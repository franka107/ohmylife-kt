package dev.pango.ohmylife.apps.mscore.infraestructure.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.charset.Charset
import java.util.UUID
import kotlin.coroutines.resume

import com.rabbitmq.client.*
import dev.pango.ohmylife.apps.mscore.application.PromptMessageDTO
import dev.pango.ohmylife.apps.mscore.application.RabbitMessageDTO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.coroutines.resume



class IaClient(private val channel: Channel) {

//    suspend fun sendAndWait(prompt: String): String = suspendCancellableCoroutine { continuation ->
//        val requestQueue = "ia_queue"
//        val responseQueue = channel.queueDeclare("", false, true, true, null).queue
//        val correlationId = UUID.randomUUID().toString()
//
//        val consumer = object : DefaultConsumer(channel) {
//            override fun handleDelivery(
//                consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?
//            ) {
//                if (properties?.correlationId == correlationId) {
//                    continuation.resume(body?.toString(Charsets.UTF_8) ?: "")
//                }
//            }
//        }
//
//        channel.basicConsume(responseQueue, true, consumer)
//
//        val props = AMQP.BasicProperties.Builder()
//            .correlationId(correlationId)
//            .replyTo(responseQueue)
//            .contentType("application/json") // 🔹 Especificar el tipo de contenido
//            .build()
//
//        // 🔹 Aquí corregimos la estructura del mensaje
//        val payload  = Json.encodeToString(RabbitMessageDTO<PromptMessageDTO>(
//            pattern = "run_prompt",
//            data = PromptMessageDTO(
//                prompt = prompt
//            )
//        ))
//
//        channel.basicPublish("", requestQueue, props, payload.toByteArray(Charsets.UTF_8))
//
//        continuation.invokeOnCancellation {
//            channel.basicCancel(consumer.consumerTag)
//        }
//    }
    suspend fun sendAndWait(prompt: String): String = suspendCancellableCoroutine { continuation ->
        val responseQueue = channel.queueDeclare("", false, true, true, null).queue // 🛑 ¿Se está creando bien?
        val correlationId = UUID.randomUUID().toString()

        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?
            ) {
                if (properties?.correlationId == correlationId) {
                    continuation.resume(body?.toString(Charsets.UTF_8) ?: "")
                }
            }
        }

        // 🛑 Verifica que estás consumiendo la respuesta en la cola correcta
        channel.basicConsume(responseQueue, true, consumer)

        val props = AMQP.BasicProperties.Builder()
            .correlationId(correlationId)
            .replyTo(responseQueue) // ✅ Esto debe coincidir con donde Ktor escucha
            .contentType("application/json")
            .build()

        val payload = Json.encodeToString(RabbitMessageDTO(
            pattern = "run_prompt",
            data = PromptMessageDTO(prompt)
        ))

        channel.basicPublish("", "ia_queue", props, payload.toByteArray(Charsets.UTF_8))

        continuation.invokeOnCancellation {
            channel.basicCancel(consumer.consumerTag)
        }
    }
}