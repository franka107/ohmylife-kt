package dev.pango.ohmylife.features.sharedkernel.domain.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

data class TaskDomain(
    val id: String,
    val title: String,
    val description: String?,
    var startedAt: Instant?,
    var elapsedTimeInMillis: Long,
    var finishedAt: Instant?,
    var pausedAt: Instant?,
    val priority: TaskPriority,
    val categoryType: TaskCategoryType?,
    val categoryReason: String?,
    val rewardMoney: Int?,
    val experiencePoints: Int?,
    val difficultyPoints: Int?,
    val difficultyReason: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    fun start() {
        if (startedAt == null) {
            startedAt = Clock.System.now()
        }
        if (pausedAt != null) {
            // Si estaba pausada, ajustamos el tiempo para continuar
            val now = Clock.System.now()
            elapsedTimeInMillis += (now - pausedAt!!).inWholeMilliseconds
            pausedAt = null
        }
    }

    fun pause() {
        if (startedAt != null && pausedAt == null) {
            pausedAt = Clock.System.now()
        }
    }

    fun stop() {
        if (startedAt != null && finishedAt == null) {
            val now = Clock.System.now()
            elapsedTimeInMillis += when {
                pausedAt != null -> (pausedAt!! - startedAt!!).inWholeMilliseconds
                else -> (now - startedAt!!).inWholeMilliseconds
            }
            finishedAt = now
        }
    }
}