package dev.pango.ohmylife.features.sharedkernel.application.dto

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String?,
    val startedAt: Instant?,
    val elapsedTimeInMillis: Long,
    val finishedAt: Instant?,
    val pausedAt: Instant?,
    val priority: String,
    val categoryType: String?,
    val categoryReason: String?,
    val rewardMoney: Int?,
    val experiencePoints: Int?,
    val difficultyPoints: Int?,
    val difficultyReason: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)