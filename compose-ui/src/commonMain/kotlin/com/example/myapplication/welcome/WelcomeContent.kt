package com.example.myapplication.welcome

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.myapplication.shared.welcome.PreviewWelcomeComponent
import com.example.myapplication.shared.welcome.WelcomeComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WelcomeContent(
    component: WelcomeComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome Screen") },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back button",
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { component.onUpdateGreetingText() },
            ) {
                Text(model.greetingText)
            }
        }
    }
}

@Preview
@Composable
fun WelcomePreview() {
    WelcomeContent(PreviewWelcomeComponent)
}
