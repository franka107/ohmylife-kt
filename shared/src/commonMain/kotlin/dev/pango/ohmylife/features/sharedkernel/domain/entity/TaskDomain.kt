package dev.pango.ohmylife.features.sharedkernel.domain.entity

import kotlinx.datetime.LocalDateTime

data class TaskDomain(
    val id: String,
    val title: String,
    val description: String?,
    val startedAt: LocalDateTime?,
    val elapsedTimeInMillis: Long,
    val finishedAt: LocalDateTime?,
    val pausedAt: LocalDateTime?,
    val priority: TaskPriority,
    val categoryType: TaskCategoryType?,
    val categoryReason: String?,
    val rewardMoney: Int?,
    val experiencePoints: Int?,
    val difficultyPoints: Int?,
    val difficultyReason: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)