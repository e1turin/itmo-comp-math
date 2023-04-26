package io.github.e1turin.output.view.entities.graph.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.graph.model.Gu

@Composable
fun FunctionGraph(
    functionRange: ClosedRange<Gu>,
    step: Gu = 1.gu,
    density: Float = -1f,
    function: (Float) -> Float,
) {
    Canvas(
        modifier = Modifier.size(600.dp, 600.dp).clipToBounds(),
    ) {
        val maxGraphWidthInUnits = 20f

        val graphDensity: Float = if (density > 0)
            density
        else
            maxGraphWidthInUnits / size.width

        with(GraphContext(density = graphDensity)) {
            drawGrid(step = step)

            drawAxes()

            drawFunctionGraph(
                range = functionRange,
                step = (step / 10),
                strokeWidth = 3f,
                color = Color.Cyan,
                function = function
            )
        }
    }
}


@Stable
inline val Float.gu: Gu get() = Gu(this)

@Stable
inline val Double.gu: Gu get() = Gu(this.toFloat())

@Stable
inline val Int.gu: Gu get() = Gu(this.toFloat())

