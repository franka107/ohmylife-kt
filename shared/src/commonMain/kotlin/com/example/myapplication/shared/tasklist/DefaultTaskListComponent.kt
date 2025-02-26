package com.example.myapplication.shared.tasklist

import arrow.fx.coroutines.resourceScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.pango.ohmylife.features.sharedkernel.application.service.TemporalTaskRepository
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority
import dev.pango.ohmylife.features.sharedkernel.mapper.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.coroutines.CoroutineContext


class DefaultTaskListComponent(
    private val componentContext: ComponentContext,
    //mainContext: CoroutineContext,
    private val temporalTaskRepository: TemporalTaskRepository,
    private val onFinished: () -> Unit,
) : TaskListComponent, ComponentContext by componentContext {

    // Consider preserving and managing the state via a store
    private val state = MutableValue(TaskListComponent.Model())
    override val model: Value<TaskListComponent.Model> = state
    //private val scope = coroutineScope(mainContext + SupervisorJob())
    private val scope = CoroutineScope(Dispatchers.Main)



    override fun onAddButtonClicked() {
        TODO("Not yet implemented")
    }

    init {
        loadTasks()
    }

   private fun loadTasks() {
       scope.launch {
           val tasks = temporalTaskRepository.getTasks().map { it.toDomain() }
           val mockTasks = listOf(TaskDomain(
               id = "1",
               title = "Probando",
               description = "a description",
               startedAt = Clock.System.now() ,
               elapsedTimeInMillis = 0,
               finishedAt = null,
               pausedAt = null,
               priority = TaskPriority.LOW,
               categoryType = TaskCategoryType.WORK,
               categoryReason = "Probbnao",
               rewardMoney = 10,
               experiencePoints = 10,
               difficultyPoints = 20,
               difficultyReason = "test",
               createdAt = Clock.System.now(),
               updatedAt = Clock.System.now() ,
           ))
           state.update { it.copy(taskList = tasks) }
       }
   }

    override fun onBackClicked() {
        onFinished()
    }

    override fun onRefreshButtonClicked() {
        scope.launch {
            loadTasks()
        }
    }

    override fun onPlayTaskButtonClicked(taskId: String) {
        scope.launch {
            temporalTaskRepository.playTask(taskId)
        }
    }

    override fun onPauseTaskButtonClicked(taskId: String) {
        scope.launch {
            temporalTaskRepository.pauseTask(taskId)
        }
    }

    override fun onStopTaskButtonClicked(taskId: String) {
        scope.launch {
            temporalTaskRepository.stopTask(taskId)
        }
    }
}
