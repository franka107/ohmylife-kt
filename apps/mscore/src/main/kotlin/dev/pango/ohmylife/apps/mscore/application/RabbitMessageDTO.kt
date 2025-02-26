package dev.pango.ohmylife.apps.mscore.application


import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RabbitMessageDTO<T>(
    val pattern: String,
    val data: T,
)

@Serializable
data class PromptMessageDTO(
    val prompt: String
)
