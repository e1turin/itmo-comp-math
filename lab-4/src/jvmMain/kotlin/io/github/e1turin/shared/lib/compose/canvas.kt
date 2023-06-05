package io.github.e1turin.shared.lib.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface


class Position(val x: Float, val y: Float)

fun DrawScope.drawText(
    text: String,
    position: Position,
    color: Color,
    font: Font = Font(Typeface.makeDefault(), 10F),
) {
    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.drawString(
            s = text,
            x = position.x,
            y = position.y,
            font = font,
            paint = color.toPaint()
        )
    }
}
