package io.github.e1turin.output.view.features.present_solution.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.features.present_solution.ui.method.equation.NewtonEquationSolution
import io.github.e1turin.output.view.features.present_solution.ui.method.equation.SIEquationSolution
import io.github.e1turin.output.view.features.present_solution.ui.method.system.SISystemSolution

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
        is SIEquationSettings -> SIEquationSolution(modifier, settings)

        DefaultEquationSettings -> {/* Unreachable case */
        }
    }
}

@Composable
private fun SystemSolutionPanel(
    modifier: Modifier,
    settings: SystemSettings
) {
    when (settings) {
        is SISystemSettings -> SISystemSolution(modifier, settings)

        DefaultSystemSettings -> {/* Unreachable case */
        }
    }
}