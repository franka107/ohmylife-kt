package dev.pango.ohmylife.features.sharedkernel.mapper

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain


fun List<TaskDomain>.toDto() = this.map { it.toDto() }

fun TaskDomain.toDto() = TaskDto(
    id = this.id,
    title = this.title,
    description = this.description,
    startedAt = this.startedAt,
    elapsedTimeInMillis = this.elapsedTimeInMillis,
    finishedAt = this.finishedAt,
    pausedAt = this.pausedAt,
    priority = this.priority.name,
    categoryType = this.categoryType?.name,
    rewardMoney = this.rewardMoney,
    experiencePoints = this.experiencePoints,
    difficultyPoints = this.difficultyPoints,
    difficultyReason = this.difficultyReason,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    categoryReason = this.categoryReason
)
