package dev.pango.ohmylife.apps.mscore.application

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CategorizedTaskInfoDTO(
    val taskId: String,
    val theme: String,
    val price: Int,
    val experience: Int,
)
