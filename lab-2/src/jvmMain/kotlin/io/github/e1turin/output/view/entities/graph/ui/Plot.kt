package io.github.e1turin.output.view.entities.graph.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.entities.settings.model.*

@Composable
fun Plot(
    modifier: Modifier = Modifier,
    model: Settings
) {
    Box(modifier = modifier) {
        when (model) {
            is DefaultSettings -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Select a task first")
            }

            is EquationSettings -> EquationPlot(settings = model)
            is SystemSettings -> SystemPlot(settings = model)
        }
    }
}

@Composable
private fun EquationPlot(
    modifier: Modifier = Modifier,
    settings: EquationSettings
) {
    when (settings) {
        is NewtonSettings -> NewtonMethodPlot(modifier = modifier, settings = settings)
        DefaultEquationSettings -> {/* impossible way due to usage contract */
        }
    }
}

@Composable
private fun SystemPlot(
    modifier: Modifier = Modifier,
    settings: SystemSettings
) {
    when (settings) {
        is SystemSimpleIterationSettings -> TODO()
        DefaultSystemSettings -> {/* impossible way due to usage contract */
        }
    }
}
