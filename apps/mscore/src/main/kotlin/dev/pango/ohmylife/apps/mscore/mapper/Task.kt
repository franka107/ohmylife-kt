package dev.pango.ohmylife.apps.mscore.mapper

import dev.pango.ohmylife.apps.mscore.application.TaskDto
import dev.pango.ohmylife.core.appdata.db.Task
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.ZoneOffset


fun Task.toTaskDomain() = TaskDomain(
    id = this.id,
    title = this.title,
    description = this.description,
    workedTime = this.worked_time,
    priority = TaskDomain.Priority.valueOf(this.priority),
    theme = TaskDomain.Theme.valueOf(this.theme),
    price = this.price,
    experience = this.experience,
    startedAt = this.started_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
    finishedAt = this.finished_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
    createdAt = this.created_at.toLocalDateTime().toKotlinLocalDateTime(),
    updatedAt = this.updated_at.toLocalDateTime().toKotlinLocalDateTime(),
    pausedAt = this.paused_at?.toLocalDateTime()?.toKotlinLocalDateTime()
)

fun TaskDomain.toTask() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    worked_time = this.workedTime,
    priority = this.priority.name,
    theme = this.theme.name,
    price = this.price,
    experience = this.experience,
    started_at = this.startedAt?.toJavaLocalDateTime()?.atOffset(ZoneOffset.UTC),
    finished_at = this.finishedAt?.toJavaLocalDateTime()?.atOffset(ZoneOffset.UTC),
    created_at = this.createdAt.toJavaLocalDateTime().atOffset(ZoneOffset.UTC),
    updated_at = this.updatedAt.toJavaLocalDateTime().atOffset(ZoneOffset.UTC),
    paused_at = this.updatedAt.toJavaLocalDateTime().atOffset(ZoneOffset.UTC)
)


fun TaskDomain.toDto() = TaskDto(
    id = this.id,
    title = this.title,
    description = this.description,
    startedAt = this.startedAt,
    pausedAt = this.pausedAt,
    workedTime = this.workedTime,
    finishedAt = this.finishedAt,
    priority = this.priority.name,
    theme = this.theme.name,
    price = this.price,
    experience = this.experience,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun List<TaskDomain>.toDto() = this.map { it.toDto() }


fun TaskDto.toDomain() = TaskDomain(
    id = this.id,
    title = this.title,
    description = this.description,
    startedAt = this.startedAt,
    pausedAt = this.pausedAt,
    workedTime = this.workedTime,
    finishedAt = this.finishedAt,
    priority = TaskDomain.Priority.valueOf(this.priority),
    theme = TaskDomain.Theme.valueOf(this.theme),
    price = this.price,
    experience = this.experience,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)
