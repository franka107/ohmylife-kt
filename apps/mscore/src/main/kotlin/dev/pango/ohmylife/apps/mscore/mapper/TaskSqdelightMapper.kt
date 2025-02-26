package dev.pango.ohmylife.apps.mscore.mapper

import dev.pango.ohmylife.core.appdata.db.Task
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.ZoneOffset


fun Task.toTaskDomain(): TaskDomain {
    return TaskDomain(
        id = id,
        title = title,
        description = description,
        startedAt = started_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
        elapsedTimeInMillis = elapsed_time_in_millis,
        finishedAt = finished_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
        pausedAt = paused_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
        priority = TaskPriority.valueOf(priority),
        categoryType = category_type?.let { TaskCategoryType.valueOf(it) },
        categoryReason = category_reason,
        rewardMoney = reward_money,
        experiencePoints = experience_points,
        difficultyPoints = difficulty_points,
        difficultyReason = difficulty_reason,
        createdAt = this.created_at.toLocalDateTime().toKotlinLocalDateTime(),
        updatedAt = this.updated_at.toLocalDateTime().toKotlinLocalDateTime(),
    )
}


fun TaskDomain.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        started_at = startedAt?.toJavaLocalDateTime()?.atOffset(ZoneOffset.UTC),
        elapsed_time_in_millis = elapsedTimeInMillis,
        finished_at = finishedAt?.toJavaLocalDateTime()?.atOffset(ZoneOffset.UTC),
        paused_at = pausedAt?.toJavaLocalDateTime()?.atOffset(ZoneOffset.UTC),
        priority = priority.name,
        category_type = categoryType?.name,
        category_reason = categoryReason,
        reward_money = rewardMoney,
        experience_points = experiencePoints,
        difficulty_points = difficultyPoints,
        difficulty_reason = difficultyReason,
        created_at = createdAt.toJavaLocalDateTime().atOffset(ZoneOffset.UTC),
        updated_at = updatedAt.toJavaLocalDateTime().atOffset(ZoneOffset.UTC),
    )
}


