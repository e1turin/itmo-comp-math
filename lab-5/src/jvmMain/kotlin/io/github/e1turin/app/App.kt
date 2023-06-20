package io.github.e1turin.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.pages.MainActivity
import io.github.e1turin.pages.MainPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun App() {
    MainPage(
        model = MainActivity(scope = CoroutineScope(Dispatchers.Default)),
        modifier = Modifier.run { fillMaxSize().padding(5.dp) }
    )
}