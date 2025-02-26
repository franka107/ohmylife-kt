package dev.pango.ohmylife.apps.mscore.mapper

import dev.pango.ohmylife.core.appdata.db.Task
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset


//fun Task.toTaskDomain(): TaskDomain {
//    return TaskDomain(
//        id = id,
//        title = title,
//        description = description,
//        startedAt = started_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
//        elapsedTimeInMillis = elapsed_time_in_millis,
//        finishedAt = finished_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
//        pausedAt = paused_at?.toLocalDateTime()?.toKotlinLocalDateTime(),
//        priority = TaskPriority.valueOf(priority),
//        categoryType = category_type?.let { TaskCategoryType.valueOf(it) },
//        categoryReason = category_reason,
//        rewardMoney = reward_money,
//        experiencePoints = experience_points,
//        difficultyPoints = difficulty_points,
//        difficultyReason = difficulty_reason,
//        createdAt = this.created_at.toLocalDateTime().toKotlinLocalDateTime(),
//        updatedAt = this.updated_at.toLocalDateTime().toKotlinLocalDateTime(),
//    )
//}
fun convertToKotlinInstant(offsetDateTime: OffsetDateTime): Instant {
    return Instant.fromEpochSeconds(offsetDateTime.toEpochSecond(), offsetDateTime.nano)
}

fun Instant.toJavaOffsetDateTime(): OffsetDateTime {
    return this.toJavaInstant().atOffset(ZoneOffset.UTC)
}

fun Task.toTaskDomain(): TaskDomain {
    return TaskDomain(
        id = id,
        title = title,
        description = description,
        startedAt = started_at?.let { convertToKotlinInstant(it) },
        elapsedTimeInMillis = elapsed_time_in_millis,
        finishedAt = finished_at?.let { convertToKotlinInstant(it) },
        pausedAt = paused_at?.let { convertToKotlinInstant(it) },
        priority = TaskPriority.valueOf(priority),
        categoryType = category_type?.let { TaskCategoryType.valueOf(it) },
        categoryReason = category_reason,
        rewardMoney = reward_money,
        experiencePoints = experience_points,
        difficultyPoints = difficulty_points,
        difficultyReason = difficulty_reason,
        createdAt = convertToKotlinInstant(created_at),
        updatedAt = convertToKotlinInstant(updated_at),
    )
}


fun TaskDomain.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        started_at = startedAt?.toJavaOffsetDateTime(),
        elapsed_time_in_millis = elapsedTimeInMillis,
        finished_at = finishedAt?.toJavaOffsetDateTime(),
        paused_at = pausedAt?.toJavaOffsetDateTime(),
        priority = priority.name,
        category_type = categoryType?.name,
        category_reason = categoryReason,
        reward_money = rewardMoney,
        experience_points = experiencePoints,
        difficulty_points = difficultyPoints,
        difficulty_reason = difficultyReason,
        created_at = createdAt.toJavaOffsetDateTime(),
        updated_at = updatedAt.toJavaOffsetDateTime(),
    )
}


