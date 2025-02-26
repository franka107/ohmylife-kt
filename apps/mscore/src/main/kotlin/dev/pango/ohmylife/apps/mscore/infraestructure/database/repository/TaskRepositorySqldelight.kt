package dev.pango.ohmylife.apps.mscore.infraestructure.database.repository

import arrow.core.Either
import dev.pango.ohmylife.apps.mscore.mapper.toTask
import dev.pango.ohmylife.apps.mscore.mapper.toTaskDomain
import dev.pango.ohmylife.core.app_data.db.AppDataDatabase
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.failure.AppFailure
import dev.pango.ohmylife.features.sharedkernel.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.io.discardingSink

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

    override fun getTask(id: String): Either<AppFailure, TaskDomain> = Either.catch {
        database.appDataQueries.getTask(id).executeAsOne().toTaskDomain()
    }.mapLeft { AppFailure.DatabaseFailure(it) }

    override fun updateTask(task: TaskDomain): Either<AppFailure, Unit> = Either.catch {
        val databaseTask = task.toTask()
        database.appDataQueries.updateTask(
            id = databaseTask.id,
            title = databaseTask.title,
            description = databaseTask.description,
            started_at = databaseTask.started_at,
            paused_at = databaseTask.paused_at,
            finished_at = databaseTask.finished_at,
            priority = databaseTask.priority,
            elapsed_time_in_millis = databaseTask.elapsed_time_in_millis,
            category_type = databaseTask.category_type,
            category_reason = databaseTask.category_reason,
            reward_money = databaseTask.reward_money,
            experience_points = databaseTask.experience_points,
            difficulty_points = databaseTask.difficulty_points,
            difficulty_reason = databaseTask.difficulty_reason,
            updated_at = databaseTask.updated_at,
        )
    }.mapLeft { AppFailure.DatabaseFailure(it) }


    override fun createTask(task: TaskDomain): Either<AppFailure, String> =   Either.catch {
        val databaseTask = task.toTask()
        database.appDataQueries.insertTask(
            id = databaseTask.id,
            title = databaseTask.title,
            description = databaseTask.description,
            started_at = databaseTask.started_at,
            paused_at = databaseTask.paused_at,
            finished_at = databaseTask.finished_at,
            priority = databaseTask.priority,
            created_at = databaseTask.created_at,
            elapsed_time_in_millis = databaseTask.elapsed_time_in_millis,
            category_type = databaseTask.category_type,
            category_reason = databaseTask.category_reason,
            reward_money = databaseTask.reward_money,
            experience_points = databaseTask.experience_points,
            difficulty_points = databaseTask.difficulty_points,
            difficulty_reason = databaseTask.difficulty_reason,
            updated_at = databaseTask.updated_at,
        )
        databaseTask.id
    }.mapLeft { AppFailure.DatabaseFailure(it) }




}