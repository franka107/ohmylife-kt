package dev.pango.ohmylife.apps.mscore.application

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String?,
    val startedAt: LocalDateTime?,
    val pausedAt: LocalDateTime?,
    val workedTime: Int,
    val finishedAt: LocalDateTime?,
    val priority: String,
    val theme: String,
    val price: Int,
    val experience: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)