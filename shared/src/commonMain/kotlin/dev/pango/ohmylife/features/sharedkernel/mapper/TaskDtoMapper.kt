package dev.pango.ohmylife.features.sharedkernel.mapper

import dev.pango.ohmylife.features.sharedkernel.application.dto.TaskDto
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority



fun TaskDto.toDomain() = TaskDomain(
    id = this.id,
    title = this.title,
    description = this.description,
    startedAt = this.startedAt,
    elapsedTimeInMillis = this.elapsedTimeInMillis,
    finishedAt = this.finishedAt,
    pausedAt = this.pausedAt,
    priority = TaskPriority.valueOf(this.priority),
    categoryType = this.categoryType?.let { TaskCategoryType.valueOf(it) },
    rewardMoney = this.rewardMoney,
    experiencePoints = this.experiencePoints,
    difficultyPoints = this.difficultyPoints,
    difficultyReason = this.difficultyReason,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    categoryReason = this.categoryReason
)
