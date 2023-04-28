package io.github.e1turin.output.view.entities.graph.ui.method

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.graph.ui.FunctionGraph
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.shared.lib.plot.toGu

@Composable
fun NewtonMethodPlot(
    modifier: Modifier = Modifier,
    settings: NewtonEquationSettings
) {
    val data by settings.data.subscribeAsState()

    FunctionGraph(
        modifier = modifier,
        inspectingRange = data.range.toGu(),
    ) {
        val f = data.function ?: throw IllegalArgumentException("Function to draw must not be null")
        f(it.toDouble()).toFloat()
    }
}