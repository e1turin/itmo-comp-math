package io.github.e1turin.output.view.features.draw_plot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.features.draw_plot.ui.method.equation.ChordEquationPlot
import io.github.e1turin.output.view.features.draw_plot.ui.method.equation.NewtonEquationPlot
import io.github.e1turin.output.view.features.draw_plot.ui.method.equation.SIEquationPlot
import io.github.e1turin.output.view.features.draw_plot.ui.method.system.SISystemMethodPlot

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

        is EquationSettings -> EquationPlot(modifier, model)
        is SystemSettings -> SystemPlot(modifier, model)
    }
}

@Composable
private fun EquationPlot(
    modifier: Modifier = Modifier,
    settings: EquationSettings
) {
    when (settings) {
        is NewtonEquationSettings -> NewtonEquationPlot(modifier, settings)
        is SIEquationSettings -> SIEquationPlot(modifier, settings)
        is ChordEquationSettings -> ChordEquationPlot(modifier, settings)

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
        is SISystemSettings -> SISystemMethodPlot(modifier, settings)

        DefaultSystemSettings -> {/* impossible way due to usage contract */
        }
    }
}
