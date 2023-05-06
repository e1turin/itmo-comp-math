package io.github.e1turin.output.view.entities.plot.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import io.github.e1turin.output.view.entities.plot.model.Gu
import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlin.math.max
import kotlin.math.roundToInt



@Composable
fun FunctionPlot1(
    modifier: Modifier = Modifier,
    inspectingRange: ClosedRange<Gu>,
    step: Gu = 1.gu,
    gridDensity: Float = -1F,
    function: (Float) -> Float,
) {
    Canvas(
        modifier = modifier.clipToBounds(),
    ) {
        val maxGraphWidthInUnits = 20F

        val graphDensity: Float = if (gridDensity > 0)
            gridDensity
        else
            maxGraphWidthInUnits / size.width


        with(GraphContext(density = graphDensity)) {
            drawGrid(step = step)

            drawAxes()

            drawFunctionGraph(
                range = inspectingRange,
                step = (step / 10),
                strokeWidth = 5F,
                color = Color.Cyan,
                function = function
            )
        }
    }
}

@Composable
fun SimpleFunctionPlot(
    modifier: Modifier = Modifier,
    inspectingRange: ClosedRange<Float>,
    step: Float,
    gridDensity: Float,
    scale: Float,
    translateTopLeft: Settings.Translation,
    function: (Float) -> Float
) {
    Canvas(
        modifier = modifier.clipToBounds(),
    ) {
        withTransform({
            scale(scale = scale)
            translate(
                translateTopLeft.offsetX.toFloat(),
                translateTopLeft.offsetY.toFloat()
            )
        }) {

            //grid
            run {
                val times = (max(size.height, size.width) / 2 / gridDensity).roundToInt()
                repeat(times) {
                    val offset = step * (it + 1)

                    // horizontal
                    drawLine(
                        start = Offset(x = .0f, y = center.y - offset),
                        end = Offset(x = size.width, y = center.y - offset),
                        color = Color.Black,
                        strokeWidth = 1f
                    )

                    drawLine(
                        start = Offset(x = .0f, y = center.y + offset),
                        end = Offset(x = size.width, y = center.y + offset),
                        color = Color.Black,
                        strokeWidth = 1f
                    )

                    // vertical
                    drawLine(
                        start = Offset(x = center.x - offset, y = .0f),
                        end = Offset(x = center.x - offset, y = size.height),
                        color = Color.Black,
                        strokeWidth = 1f
                    )

                    drawLine(
                        start = Offset(x = center.x + offset, y = .0f),
                        end = Offset(x = center.x + offset, y = size.height),
                        color = Color.Black,
                        strokeWidth = 1f
                    )
                }
            }

            //axes
            run {
                val thickness = 5F
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

            //function
            run {
                var param = inspectingRange.start
                val rangeLength = inspectingRange.endInclusive - inspectingRange.start
                val times = (rangeLength / step).roundToInt()

                repeat(times) {
                    val nextParam = param + step
                    drawLine(
                        color = Color.Cyan,
                        start = Offset(
                            x = center.x + param, y = center.y + function(param)
                        ),
                        end = Offset(
                            x = center.x + nextParam, y = center.y + function(nextParam)
                        ),
                        strokeWidth = 1F
                    )
                    param = nextParam
                }
            }
        }
    }

}


@Stable
inline val Float.gu: Gu get() = Gu(this)

@Stable
inline val Double.gu: Gu get() = Gu(this.toFloat())

@Stable
inline val Int.gu: Gu get() = Gu(this.toFloat())

