package io.github.e1turin.widgets.graph.output

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.interpolations.InterpolationsStore
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.features.graph.draw.Graph2D
import io.github.e1turin.pages.MainActivity
import io.github.e1turin.shared.lib.std.pretty

@Composable
fun GraphOutput(
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    val interpolations by InterpolationsStore.interpolations
    val inputParam by PointsStore.inspectingParam

    Box(modifier, contentAlignment = Alignment.TopEnd) {
        Column(Modifier.padding(10.dp)) {
            interpolations.forEach { interpolation ->
                interpolation.function?.let {
                    Text(
                        text = "${interpolation.print()}: P(${inputParam.pretty()}) = ${it(inputParam).pretty()}",
                        color = interpolation.color
                    )
                }
            }
        }
        Graph2D(Modifier)
    }
}