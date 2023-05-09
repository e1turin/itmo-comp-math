package io.github.e1turin.output.view.entities.plot.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import io.github.e1turin.output.view.entities.plot.model.Gu
import io.github.e1turin.output.view.entities.settings.model.Settings
import io.github.e1turin.output.view.shared.lib.compose.toPaint
import io.github.e1turin.output.view.shared.lib.plot.gu
import io.github.e1turin.output.view.shared.lib.std.length
import io.github.e1turin.output.view.shared.lib.std.pretty
import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import kotlin.math.*



@Composable
fun FunctionPlot(
    modifier: Modifier = Modifier,
    inspectingRange: ClosedFloatingPointRange<Float>,
    step: Float = 0.01F,
    function: (Float) -> Float
) {
    val padding = 80F

    Canvas(modifier = modifier.clipToBounds()) {
        val zoneWidth: Float = size.width - padding
        val scale = zoneWidth / inspectingRange.length //coefficient to multiply Graph Units and get pixels

        //function
        run {
            val points = (inspectingRange.length / step).toInt() + 1

            var current = inspectingRange.start

            repeat(points) {
                val next = current + step

                drawLine(
                    Color.Blue,
                    start = Offset(
                        x = (current - inspectingRange.start) * scale + padding,
                        y = center.y - function(current) * scale - padding / 2
                    ),
                    end = Offset(
                        x = (next - inspectingRange.start) * scale + padding,
                        y = center.y - function(next) * scale - padding / 2
                    ),
                    strokeWidth = 2F
                )

                current = next
            }
        }

        //grid + vertical axes step
        run {
            val power = log(inspectingRange.length, 10F)
            val actualGridStep = 10F.pow(floor(power).toInt() - 1)
            val gridStep = actualGridStep * scale

            val verticalLines = (zoneWidth / gridStep).toInt() + 1
            val actualFirstVertical = ceil(inspectingRange.start / actualGridStep) * actualGridStep
            var currentVertical =
                padding + (actualFirstVertical - inspectingRange.start) * scale

            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawString(
                    s = actualFirstVertical.pretty(),
                    x = currentVertical,
                    y = size.height - padding + 15F,
                    font = Font(Typeface.makeDefault(), 10F),
                    paint = Color.Black.toPaint()
                )
            }
            repeat(verticalLines) {
                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = currentVertical,
                        y = 0F,
                    ),
                    end = Offset(
                        x = currentVertical,
                        y = size.height - padding,
                    )
                )

                currentVertical += gridStep
            }

            val horizontalLines = ((size.height - padding) / gridStep).toInt() / 2
            var currentHorizontalOffset = gridStep

            repeat(horizontalLines) {
                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding,
                        y = center.y + currentHorizontalOffset - padding / 2,
                    ),
                    end = Offset(
                        x = size.width,
                        y = center.y + currentHorizontalOffset - padding / 2,
                    )
                )

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawString(
                        s = (+actualGridStep * (it + 1)).pretty(),
                        x = 10F,
                        y = center.y - currentHorizontalOffset - padding / 2,
                        font = Font(Typeface.makeDefault(), 10F),
                        paint = Color.Black.toPaint()
                    )
                }

                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding,
                        y = center.y - currentHorizontalOffset - padding / 2,
                    ),
                    end = Offset(
                        x = size.width,
                        y = center.y - currentHorizontalOffset - padding / 2,
                    )
                )

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawString(
                        s = (-actualGridStep * (it + 1)).pretty(),
                        x = 10F,
                        y = center.y + currentHorizontalOffset - padding / 2,
                        font = Font(Typeface.makeDefault(), 10F),
                        paint = Color.Black.toPaint()
                    )
                }

                currentHorizontalOffset += gridStep
            }.also {
                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding,
                        y = center.y - padding / 2,
                    ),
                    end = Offset(
                        x = size.width,
                        y = center.y - padding / 2
                    ),
                    strokeWidth = 3F
                )

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawString(
                        s = 0F.pretty(),
                        x = 10F,
                        y = center.y - padding / 2,
                        font = Font(Typeface.makeDefault(), 10F),
                        paint = Color.Black.toPaint()
                    )
                }
            }
        }

    }
}

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


