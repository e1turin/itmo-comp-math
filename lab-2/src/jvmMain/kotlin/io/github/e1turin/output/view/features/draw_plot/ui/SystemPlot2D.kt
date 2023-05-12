package io.github.e1turin.output.view.features.draw_plot.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
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
            inspectingZone = listOf(
                -4F..4F,
                -4F..4F
            ),
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
    inspectingZone: List<ClosedFloatingPointRange<Float>>,
    step: Float = 0.01F,
    system: List<(List<Double>) -> Double>
) {
    require(system.size == 2) { "The system must be possible to represent on a plane" }
    require(inspectingZone.size == 2) { "The range must be possible to represent on a plane" }

    val inspectingXRange = inspectingZone[0]
    val inspectingYRange = inspectingZone[1]

    val padding = 80F

    Canvas(modifier = modifier.clipToBounds()) {
        val zoneWidth = size.width - padding
        val zoneHeight = size.height - padding

        val scale = if (zoneWidth > zoneHeight) {
            zoneWidth / inspectingXRange.length
        } else {
            zoneHeight / inspectingYRange.length
        }

        for (x in inspectingXRange unsteadyStep step) {
            for (y in inspectingYRange unsteadyStep step) {
                if (system[0](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Red,
                        radius = 2F,
                        center = Offset(
                            x = padding + (x - inspectingXRange.start) * scale,
                            y = (y - inspectingYRange.start) * scale
                        )
                    )
                }

                if (system[1](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Blue,
                        radius = 2F,
                        center = Offset(
                            x = padding + (x - inspectingXRange.start) * scale,
                            y = (y - inspectingYRange.start) * scale
                        )
                    )
                }
            }
        }


        //grid + vertical axes step
        run {
            val usingAlignmentRange = if (zoneWidth > zoneHeight) inspectingXRange else inspectingYRange

            val power = log(usingAlignmentRange.length, 10F)

            val actualGridStep = 10F.pow(floor(power).toInt() - 1)
            val gridStep = actualGridStep * scale

            val verticalLines = (zoneWidth / gridStep).toInt() + 1
            val actualFirstVertical = ceil(inspectingXRange.start / actualGridStep) * actualGridStep

            var currentVertical = padding + (actualFirstVertical - inspectingXRange.start) * scale

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
                        x = padding - inspectingXRange.start * scale,
                        y = 0F,
                    ),
                    end = Offset(
                        x = padding - inspectingXRange.start * scale,
                        y = size.height - padding,
                    ),
                    strokeWidth = 3F
                )

            val horizontalLines = (zoneHeight / gridStep).toInt()
            val actualFirstHorizontal = ceil(inspectingYRange.start / actualGridStep) * actualGridStep

            var actualCurrentHorizontal = actualFirstHorizontal

            repeat(horizontalLines) {
                val currentHorizontal = (actualCurrentHorizontal - inspectingYRange.start) * scale

                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding,
                        y = currentHorizontal,
                    ),
                    end = Offset(
                        x = size.width,
                        y = currentHorizontal,
                    )
                )

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawString(
                        s = actualCurrentHorizontal.pretty(),
                        x = 10F,
                        y = currentHorizontal + 2F,
                        font = Font(Typeface.makeDefault(), 10F),
                        paint = Color.Black.toPaint()
                    )
                }

                actualCurrentHorizontal += actualGridStep
            }
            if (actualFirstHorizontal * (actualFirstHorizontal + horizontalLines * actualGridStep) < 0)
                drawLine(
                    Color.DarkGray,
                    start = Offset(
                        x = padding,
                        y = -inspectingYRange.start * scale,
                    ),
                    end = Offset(
                        x = size.width,
                        y = -inspectingYRange.start * scale
                    ),
                    strokeWidth = 3F
                )

            drawRect(
                color = Color.Magenta,
                topLeft = Offset(
                    x = padding + 0F,
                    y = 0F
                ),
                size = Size(
                    width = inspectingXRange.length * scale,
                    height = inspectingYRange.length * scale
                ),
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}