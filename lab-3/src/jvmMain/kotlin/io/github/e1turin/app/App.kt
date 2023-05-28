package io.github.e1turin.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import io.github.e1turin.pages.Routing

@Composable
@Preview
fun App() {
    MaterialTheme {
        Routing()
    }
}
