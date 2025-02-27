package com.example.myapplication.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.example.myapplication.main.MainContent
import com.example.myapplication.shared.root.RootComponent
import com.example.myapplication.shared.root.RootComponent.Child
import com.example.myapplication.tasklist.TaskListContent
import com.example.myapplication.welcome.WelcomeContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(slide())
            ) {
                when (val instance = it.instance) {
                    is Child.Main -> MainContent(component = instance.component)
                    is Child.Welcome -> WelcomeContent(component = instance.component)
                    is Child.TaskList -> TaskListContent(component = instance.component)
                }
            }
        }
    }
}
