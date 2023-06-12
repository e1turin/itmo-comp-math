package io.github.e1turin.feature.graph.draw

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import io.github.e1turin.shared.lib.compose.Position
import io.github.e1turin.shared.lib.compose.drawText
import io.github.e1turin.shared.lib.std.length
import io.github.e1turin.shared.lib.std.pretty
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

@Composable
fun FunctionPlot2D(
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

            // first vertical line coordinate
            drawText(
                actualFirstVertical.pretty(),
                Position(currentVertical, size.height - padding + 15F),
                color = Color.Black
            )

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
            // 'x=0' axis
            if (actualFirstVertical * (actualFirstVertical + verticalLines * actualGridStep) < 0)
                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding - inspectingRange.start * scale,
                        y = 0F,
                    ),
                    end = Offset(
                        x = padding - inspectingRange.start * scale,
                        y = size.height - padding,
                    ),
                    strokeWidth = 3F
                )

            val horizontalLines = ((size.height - padding) / gridStep).toInt() / 2
            var currentHorizontalOffset = gridStep

            repeat(horizontalLines) {
                // lower 'y=0' axis
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

                drawText(
                    (+actualGridStep * (it + 1)).pretty(),
                    Position(10F, center.y - currentHorizontalOffset - padding / 2),
                    color = Color.Black
                )

                // higher 'y=0' axis
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

                drawText(
                    (-actualGridStep * (it + 1)).pretty(),
                    Position(
                        x = 10F,
                        y = center.y + currentHorizontalOffset - padding / 2,
                    ),
                    Color.Black
                )

                currentHorizontalOffset += gridStep
            }
            // 'y=0' axis
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

            drawText(
                0F.pretty(),
                Position(
                    x = 10F,
                    y = center.y - padding / 2,
                ),
                Color.Black
            )
        }
    }
}
