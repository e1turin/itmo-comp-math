package io.github.e1turin.output.view.features.present_solution.ui.method

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
import io.github.e1turin.model.domain.equation.nonlinear.system.method.SimpleIterationSystemSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.system.solver.IterativeSystemSolver
import io.github.e1turin.model.util.vectorLength
import io.github.e1turin.output.view.entities.settings.model.SystemSimpleIterationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.features.present_solution.model.toResultString
import io.github.e1turin.output.view.shared.lib.std.prettyVector


private sealed interface SimpleIterationTestResult {
    data class Error(val e: Exception) : SimpleIterationTestResult
    data class State(val result: Boolean) : SimpleIterationTestResult
}

private fun SimpleIterationTestResult.toResultString(): String = when (this) {
    is SimpleIterationTestResult.Error -> e.message ?: "Error appeared while checking condition"
    is SimpleIterationTestResult.State -> if (result) "condition is met" else "condition is not met"
}


@Composable
fun SimpleIterationSystemSolutionPresenter(
    modifier: Modifier = Modifier,
    settings: SystemSimpleIterationSettings
) {
    val data by settings.data.subscribeAsState()

    var initialValue = data.initialValue

    val conditionState = try {
        val result = SimpleIterationSystemSolvingMethod.testConvergenceCondition(
            range = data.range,
            jacobianMatrix = listOf(
                listOf({ 1.0 }, { 2.0 }),
                listOf({ 2.0 }, { -1.0 })
            )
        )

        SimpleIterationTestResult.State(result)
    } catch (e: Exception) {
        SimpleIterationTestResult.Error(e)
    }

    val result = try {
        val method = SimpleIterationSystemSolvingMethod(
            approximationFunction = listOf(
                { x -> -2 * x[1] - 1 },
                { x -> 2 * x[0] },
            )
        )

        var step = 0
        val solver = IterativeSystemSolver(
            method = method,
            initialApproximation = data.initialValue,
            stopCondition = { deviance -> vectorLength(deviance) < 0.001 || ++step > 10000 }
        )

        val solution = solver.solve(data.system!!)

        SolvingResult.Point(solution)
    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

    val outputResultText = result.toResultString()
    val outputTestResultText = conditionState.toResultString()

    SelectionContainer {
        Column(modifier = modifier) {
            Text("Convergence condition")
                .also { Spacer(Modifier.size(10.dp)) }
            Text(outputTestResultText)

            Spacer(Modifier.size(20.dp))

            Text("Solution")
                .also { Spacer(Modifier.size(10.dp)) }
            Text("start point: ${initialValue.prettyVector()}")
            Text("result: $outputResultText")
        }
    }

}