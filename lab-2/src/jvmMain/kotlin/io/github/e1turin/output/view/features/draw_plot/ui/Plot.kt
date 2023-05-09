package io.github.e1turin.output.view.features.draw_plot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.entities.plot.ui.method.NewtonMethodPlot
import io.github.e1turin.output.view.entities.plot.ui.method.SystemSimpleIterationMethodPlot
import io.github.e1turin.output.view.entities.settings.model.*

@Composable
fun Plot(
    modifier: Modifier = Modifier,
    model: Settings
) {
    when (model) {
        is DefaultSettings -> Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text("Select a task first")
        }

        is EquationSettings -> EquationPlot(modifier = modifier, settings = model)
        is SystemSettings -> SystemPlot(modifier = modifier, settings = model)
    }
}

@Composable
private fun EquationPlot(
    modifier: Modifier = Modifier,
    settings: EquationSettings
) {
    when (settings) {
        is NewtonEquationSettings -> NewtonMethodPlot(modifier = modifier, settings = settings)
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
        is SystemSimpleIterationSettings -> SystemSimpleIterationMethodPlot(modifier = modifier, settings = settings)

        DefaultSystemSettings -> {/* impossible way due to usage contract */
        }
    }
}
