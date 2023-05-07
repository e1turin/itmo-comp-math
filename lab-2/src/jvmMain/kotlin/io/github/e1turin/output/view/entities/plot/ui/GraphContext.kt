package io.github.e1turin.output.view.entities.plot.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import io.github.e1turin.output.view.entities.plot.model.Gu
import io.github.e1turin.output.view.shared.lib.plot.gu
import kotlin.math.max
import kotlin.math.roundToInt


class GraphContext(val density: Float = 1f) {
    @Stable
    inline val Gu.px: Float get() = value / density

    fun DrawScope.drawFunctionGraph(
        range: ClosedRange<Gu>,
        step: Gu = 1.gu,
        strokeWidth: Float = 1f,
        color: Color = Color.Cyan,
        function: (Float) -> Float
    ) {
        var param = range.start
        val rangeLength = range.endInclusive - range.start
        val times = (rangeLength / step).roundToInt()

        repeat(times) {
            val nextParam = param + step
            drawLine(
                color = color,
                start = Offset(
                    x = center.x + param.px, y = center.y + function(param.value).gu.px
                ),
                end = Offset(
                    x = center.x + nextParam.px, y = center.y + function(nextParam.value).gu.px
                ),
                strokeWidth = strokeWidth
            )
            param = nextParam
        }
    }

    fun DrawScope.drawGrid(step: Gu = 1.gu) {
        val times = (max(size.height, size.width) / 2 / density).roundToInt()

        repeat(times) {
            val offset = step * (it + 1)

            // horizontal
            drawLine(
                start = Offset(x = .0f, y = center.y - offset.px),
                end = Offset(x = size.width, y = center.y - offset.px),
                color = Color.Black,
                strokeWidth = 1f
            )

            drawLine(
                start = Offset(x = .0f, y = center.y + offset.px),
                end = Offset(x = size.width, y = center.y + offset.px),
                color = Color.Black,
                strokeWidth = 1f
            )

            // vertical
            drawLine(
                start = Offset(x = center.x - offset.px, y = .0f),
                end = Offset(x = center.x - offset.px, y = size.height),
                color = Color.Black,
                strokeWidth = 1f
            )

            drawLine(
                start = Offset(x = center.x + offset.px, y = .0f),
                end = Offset(x = center.x + offset.px, y = size.height),
                color = Color.Black,
                strokeWidth = 1f
            )
        }

    }

    @OptIn(ExperimentalTextApi::class)
    fun DrawScope.drawAxes(thickness: Float = 5f) {

        // horizontal
        drawLine(
            start = Offset(x = 0.0f, y = center.y),
            end = Offset(x = size.width, y = center.y),
            color = Color.Black,
            strokeWidth = thickness
        )

        // vertical
        drawLine(
            start = Offset(x = center.x, y = 0.0f),
            end = Offset(x = center.x, y = size.height),
            color = Color.Black,
            strokeWidth = thickness
        )
    }
}
