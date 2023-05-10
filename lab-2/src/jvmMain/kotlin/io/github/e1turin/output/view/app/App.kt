package io.github.e1turin.output.view.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.e1turin.output.view.pages.main.ui.MainActivity

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainActivity()
    }
}
