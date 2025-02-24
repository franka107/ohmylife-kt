package dev.pango.ohmylife.features.sharedkernel.domain.entity

import kotlinx.datetime.LocalDateTime

data class TaskDomain(
    val id: String,
    val title: String,
    val description: String?,
    val startedAt: LocalDateTime?,
    val workedTime: Int,
    val finishedAt: LocalDateTime?,
    val pausedAt: LocalDateTime?,
    val priority: Priority,
    val theme: Theme,
    val price: Int,
    val experience: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    enum class Priority { LOW, MEDIUM, HIGH }
    enum class Theme {
        HEALTH,
        WORK,
        STUDY,
        FINANCE,
        RELATIONSHIPS,
        LEISURE,
    }
}