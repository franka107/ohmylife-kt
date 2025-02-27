package com.example.myapplication.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.shared.main.MainComponent
import com.example.myapplication.shared.main.PreviewMainComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Decompose Template") },
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Button(onClick = component::onShowWelcomeClicked) {
                Text(text = "Show Welcome screen")
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MainContent(PreviewMainComponent)
}
