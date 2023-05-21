package io.github.e1turin.output.view.pages.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.pages.main.model.MainPage

@Composable
fun MainActivity() {
    MainPage(
        modifier = Modifier.fillMaxSize(),
        model = MainPage()
    )
}