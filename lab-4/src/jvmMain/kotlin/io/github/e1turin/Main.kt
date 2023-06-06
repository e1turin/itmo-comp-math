package io.github.e1turin

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.e1turin.app.App

fun main() {
    application {
        Window(
            title = "lab-4",
            icon = painterResource("favicon.png"),
            onCloseRequest = ::exitApplication,
            state = rememberWindowState()
        ) {
            App()
        }
    }
}
