package io.github.e1turin.output.view.features.present_solution.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.model.domain.equation.nonlinear.method.NewtonSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.solver.IterativeEquationSolver
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.features.present_solution.ui.method.NewtonSolvingMethodSolutionPresenter
import io.github.e1turin.output.view.shared.lib.std.pretty
import kotlin.math.abs

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
        is NewtonEquationSettings -> NewtonSolvingMethodSolutionPresenter(modifier, settings)

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
        is SystemSimpleIterationSettings -> TODO()

        DefaultSystemSettings -> {/* Unreachable case */
        }
    }
}