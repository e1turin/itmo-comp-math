package io.github.e1turin.output.view.features.present_solution.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.features.present_solution.ui.method.NewtonEquationSolution
import io.github.e1turin.output.view.features.present_solution.ui.method.SimpleIterationSystemSolutionPresenter

@Composable
fun SolutionPanel(
    modifier: Modifier = Modifier,
    settings: Settings
) {
    when (settings) {
        is EquationSettings -> EquationSolutionPanel(modifier, settings)
        is SystemSettings -> SystemSolutionPanel(modifier, settings)

        DefaultEquationSettings, DefaultSystemSettings, UndefinedDefaultSettings -> {/* Unreachable case */
        }
    }
}

@Composable
private fun EquationSolutionPanel(
    modifier: Modifier,
    settings: EquationSettings
) {
    when (settings) {
        is NewtonEquationSettings -> NewtonEquationSolution(modifier, settings)

        DefaultEquationSettings -> {/* Unreachable case */
        }

        is SimpleIterationEquationSettings -> TODO()
    }
}

@Composable
private fun SystemSolutionPanel(
    modifier: Modifier,
    settings: SystemSettings
) {
    when (settings) {
        is SimpleIterationSystemSettings -> SimpleIterationSystemSolutionPresenter(modifier, settings)

        DefaultSystemSettings -> {/* Unreachable case */
        }
    }
}