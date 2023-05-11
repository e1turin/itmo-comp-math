package io.github.e1turin

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.e1turin.output.view.app.App


fun main() = application {
    Window(
        title = "lab-2",
        icon = painterResource("favicon.png"),
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1200.dp, height = 900.dp),
    ) {
        App()
    }
}
