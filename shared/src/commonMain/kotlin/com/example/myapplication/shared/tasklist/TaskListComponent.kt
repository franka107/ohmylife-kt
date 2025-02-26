package com.example.myapplication.shared.tasklist

import com.arkivanov.decompose.value.Value
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain

interface TaskListComponent {

    val model: Value<Model>

    fun onAddButtonClicked()
    fun onBackClicked()
     fun onPlayTaskButtonClicked(taskId: String)
     fun onPauseTaskButtonClicked(taskId: String)
     fun onStopTaskButtonClicked(taskId: String)
     fun onRefreshButtonClicked()

    data class Model(
        val taskList: List<TaskDomain> = emptyList()
    )
}
