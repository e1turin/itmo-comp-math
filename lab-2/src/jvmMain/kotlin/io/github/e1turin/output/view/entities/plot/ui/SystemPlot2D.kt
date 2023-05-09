package io.github.e1turin.output.view.entities.plot.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.form.ui.padding
import io.github.e1turin.output.view.shared.lib.compose.toPaint
import io.github.e1turin.output.view.shared.lib.std.approx
import io.github.e1turin.output.view.shared.lib.std.length
import io.github.e1turin.output.view.shared.lib.std.pretty
import io.github.e1turin.output.view.shared.lib.std.unsteadyStep
import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

@Preview
@Composable
fun SystemPlot2DPreview() {
    Box(Modifier.size(400.dp)) {
        SystemPlot2D(
            modifier = Modifier.fillMaxSize(),
            inspectingRange = -4F..4F,
            system = listOf(
                { arg -> arg[0] + arg[1] },
                { arg -> arg[0] - arg[1] }
            )
        )
    }
}


@Composable
fun SystemPlot2D(
    modifier: Modifier = Modifier,
    inspectingRange: ClosedFloatingPointRange<Float>,
    step: Float = 0.01F,
    system: List<(List<Double>) -> Double>
) {
    require(system.size == 2) { "The system must be possible to represent on a plane" }
    val padding = 80F

    Canvas(modifier = modifier.clipToBounds()) {
        val zoneWidth = size.width - padding
        val scale = zoneWidth / inspectingRange.length
        val rangeHeight = (size.height - padding)/ scale

        for (x in inspectingRange unsteadyStep step) {
            for (y in (-rangeHeight / 2)..(+rangeHeight / 2) unsteadyStep step) {
                if (system[0](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Red,
                        radius = 2F,
                        center = Offset(
                            x = padding + (x - inspectingRange.start) * scale,
                            y = center.y - y * scale - padding / 2
                        )
                    )
                }

                if (system[1](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Blue,
                        radius = 2F,
                        center = Offset(
                            x = padding + (x - inspectingRange.start) * scale,
                            y = center.y - y * scale - padding / 2
                        )
                    )
                }
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