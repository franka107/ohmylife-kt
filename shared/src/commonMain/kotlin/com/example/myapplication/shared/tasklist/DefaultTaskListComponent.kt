package com.example.myapplication.shared.tasklist

import arrow.fx.coroutines.resourceScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.pango.ohmylife.features.sharedkernel.application.service.TemporalTaskRepository
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.pango.ohmylife.features.sharedkernel.mapper.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
           state.update { it.copy(taskList = tasks) }
       }
   }

    override fun onBackClicked() {
        onFinished()
    }
}
