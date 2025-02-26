package com.example.myapplication.shared.tasklist

import com.arkivanov.decompose.value.MutableValue


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
}
