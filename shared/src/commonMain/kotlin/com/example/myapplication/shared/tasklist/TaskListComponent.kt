package com.example.myapplication.shared.tasklist

import com.arkivanov.decompose.value.Value
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain

interface TaskListComponent {

    val model: Value<Model>

    fun onAddButtonClicked()
    fun onBackClicked()

    data class Model(
        val taskList: List<TaskDomain> = emptyList()
    )
}
