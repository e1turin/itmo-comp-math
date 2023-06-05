package io.github.e1turin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.e1turin.app.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
