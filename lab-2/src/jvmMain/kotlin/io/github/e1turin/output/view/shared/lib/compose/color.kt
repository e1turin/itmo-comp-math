package io.github.e1turin.output.view.shared.lib.compose

import androidx.compose.ui.graphics.Color
import kotlin.random.Random as Rand


val Color.Companion.Random: Color get() {
    val red = Rand.nextInt(256)
    val green = Rand.nextInt(256)
    val blue = Rand.nextInt(256)
    return Color(red, green, blue)
}