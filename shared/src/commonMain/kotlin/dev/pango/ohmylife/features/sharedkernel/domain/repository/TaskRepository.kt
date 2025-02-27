package dev.pango.ohmylife.features.sharedkernel.domain.repository

import arrow.core.Either
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.failure.AppFailure
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTaskListFlow(): Flow<List<TaskDomain>>
    fun getTaskList(): Either<AppFailure, List<TaskDomain>>
    fun getTask(id: String): Either<AppFailure, TaskDomain>
    fun createTask(task: TaskDomain): Either<AppFailure, String>
    fun updateTask(task: TaskDomain): Either<AppFailure, Unit>
}