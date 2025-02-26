package com.example.myapplication.shared.tasklist

import com.arkivanov.decompose.value.MutableValue


object PreviewTaskListComponent : TaskListComponent {
    override val model = MutableValue(TaskListComponent.Model())

    override fun onAddButtonClicked() {}

    override fun onBackClicked() {}
}
