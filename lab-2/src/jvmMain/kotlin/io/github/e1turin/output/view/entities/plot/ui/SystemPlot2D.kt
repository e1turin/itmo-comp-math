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
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.shared.lib.std.approx
import io.github.e1turin.output.view.shared.lib.std.length
import io.github.e1turin.output.view.shared.lib.std.unsteadyStep

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

    Canvas(modifier = modifier.clipToBounds()) {
        val zoneWidth = size.width
        val scale = zoneWidth / inspectingRange.length
        val rangeHeight = size.height / scale

        for (x in inspectingRange unsteadyStep step) {
            for (y in (-rangeHeight / 2)..(+rangeHeight / 2) unsteadyStep step) {
                if (system[0](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Red,
                        radius = 2F,
                        center = Offset((x - inspectingRange.start) * scale, center.y - y * scale)
                    )
                }

                if (system[1](listOf(x.toDouble(), y.toDouble())) approx 0.0) {
                    drawCircle(
                        Color.Blue,
                        radius = 2F,
                        center = Offset((x - inspectingRange.start) * scale, center.y - y * scale)
                    )
                }
            }
        }
    }
}