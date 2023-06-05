package io.github.e1turin.app

import androidx.compose.runtime.Composable
import io.github.e1turin.pages.main.MainActivity
import io.github.e1turin.pages.main.MainPageUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun App() {
    MainPageUI(MainActivity(CoroutineScope(Dispatchers.Default)))
}