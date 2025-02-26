package com.example.myapplication.shared.tasklist

import com.arkivanov.decompose.value.MutableValue
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority


object PreviewTaskListComponent : TaskListComponent {
    override val model = MutableValue(TaskListComponent.Model())

    override fun onAddButtonClicked() {}

    override fun onBackClicked() {}
    override fun onPlayTaskButtonClicked(taskId: String) {
    }

    override fun onPauseTaskButtonClicked(taskId: String) {
    }

    override fun onStopTaskButtonClicked(taskId: String) {
    }

    override fun onRefreshButtonClicked() {

    }

    override fun onCreateTaskButtonClicked(
        taskTitle: String,
        taskDescription: String?,
        taskPriority: TaskPriority
    ) {
    }
}
