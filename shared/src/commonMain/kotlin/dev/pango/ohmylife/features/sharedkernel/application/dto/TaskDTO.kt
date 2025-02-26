package dev.pango.ohmylife.features.sharedkernel.application.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String?,
    val startedAt: LocalDateTime?,
    val elapsedTimeInMillis: Long,
    val finishedAt: LocalDateTime?,
    val pausedAt: LocalDateTime?,
    val priority: String,
    val categoryType: String?,
    val categoryReason: String?,
    val rewardMoney: Int?,
    val experiencePoints: Int?,
    val difficultyPoints: Int?,
    val difficultyReason: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)