package io.github.e1turin.output.view.features.draw_plot.ui.method.equation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.features.draw_plot.ui.FunctionPlot2D
import io.github.e1turin.output.view.shared.lib.std.toFloatRange

@Composable
fun NewtonEquationPlot(
    modifier: Modifier = Modifier,
    settings: NewtonEquationSettings
) {
    val data by settings.data.subscribeAsState()

    FunctionPlot2D(
        modifier = modifier,
        inspectingRange = data.range.toFloatRange(),
    ) { x: Float ->
        val f = data.function ?: throw IllegalArgumentException("Function to draw must not be null")
        f(x.toDouble()).toFloat()
    }
}