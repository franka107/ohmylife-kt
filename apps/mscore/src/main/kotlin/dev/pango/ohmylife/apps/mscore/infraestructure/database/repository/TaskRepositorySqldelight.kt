package dev.pango.ohmylife.apps.mscore.infraestructure.database.repository

import arrow.core.Either
import dev.pango.ohmylife.apps.mscore.mapper.toTask
import dev.pango.ohmylife.apps.mscore.mapper.toTaskDomain
import dev.pango.ohmylife.core.app_data.db.AppDataDatabase
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.failure.AppFailure
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositorySqldelight(
    private val database: AppDataDatabase,
): TaskRepository {
    override fun getTaskListFlow(): Flow<List<TaskDomain>> {
        TODO("Not yet implemented")
    }

    override fun getTaskList(): Either<AppFailure, List<TaskDomain>>  =  Either.catch {
        database.appDataQueries.getTaskList().executeAsList().map {
            it.toTaskDomain()
        }
    }.mapLeft {AppFailure.DatabaseFailure(it) }


    override suspend fun createTask(task: TaskDomain): Either<AppFailure, String> =   Either.catch {
        val databaseTask = task.toTask()
        database.appDataQueries.insertTask(
            id = databaseTask.id,
            title = databaseTask.title,
            description = databaseTask.description,
            started_at = databaseTask.started_at,
            paused_at = databaseTask.paused_at,
            worked_time = databaseTask.worked_time,
            finished_at = databaseTask.finished_at,
            priority = databaseTask.priority,
            theme = databaseTask.theme,
            price = databaseTask.price,
            experience = databaseTask.experience,
            created_at = databaseTask.created_at,
            updated_at = databaseTask.updated_at
        )
        ""
    }.mapLeft { AppFailure.DatabaseFailure(it) }

    override suspend fun updateTask(task: TaskDomain): Either<AppFailure, Nothing> {
        TODO("Not yet implemented")
    }
}