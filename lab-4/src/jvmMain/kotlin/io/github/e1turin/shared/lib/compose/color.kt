package io.github.e1turin.shared.lib.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skia.Paint


internal fun Color.toPaint(): Paint = Paint().apply {
    isAntiAlias = true
    color = toArgb()
}