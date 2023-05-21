package io.github.e1turin.output.view.features.present_solution.ui.method.equation

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
import io.github.e1turin.model.domain.equation.nonlinear.method.SimpleIterationSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.solver.IterativeEquationSolver
import io.github.e1turin.output.view.entities.settings.model.SIEquationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.features.present_solution.model.toResultString
import io.github.e1turin.output.view.shared.lib.std.pretty
import kotlin.math.abs


private sealed interface SimpleIterationTestResult {
    data class Error(val e: Exception) : SimpleIterationTestResult
    data class State(val result: Boolean) : SimpleIterationTestResult
}

private fun SimpleIterationTestResult.toResultString(): String = when (this) {
    is SimpleIterationTestResult.Error -> e.message ?: "Error appeared while checking condition"
    is SimpleIterationTestResult.State -> if (result) "condition is met" else "condition is not met"
}


@Composable
fun SIEquationSolution(
    modifier: Modifier = Modifier,
    settings: SIEquationSettings
) {
    val data by settings.data.subscribeAsState()

    val initialValue: Double = data.initialValue

    val conditionState = try {
        val result = SimpleIterationSolvingMethod.testConvergenceCondition(
            range = data.range,
            derivative = data.derivativeFunction!!
        )

        SimpleIterationTestResult.State(result)
    } catch (e: Exception) {
        SimpleIterationTestResult.Error(e)
    }

    val solvingResult: SolvingResult = try {
        val method = SimpleIterationSolvingMethod(
            approximationFunction = data.approximationFunction!!,
        )

        var step = 0
        val solver = IterativeEquationSolver(
            method = method,
            initialApproximation = data.initialValue,
            stopCondition = { eps: Double -> abs(eps) < 0.001 || ++step > 10000 }
        )

        val solution = solver.solve(data.function!!)

        SolvingResult.Number(solution)
    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

    val outputResultText: String = solvingResult.toResultString()
    val outputTestResultText = conditionState.toResultString()

    SelectionContainer {
        Column(modifier = modifier) {
            Text("Convergence condition")
                .also { Spacer(Modifier.size(10.dp)) }
            Text(outputTestResultText)

            Spacer(Modifier.size(20.dp))

            Text("Solution")
                .also { Spacer(Modifier.size(10.dp)) }
            Text("initial value: ${initialValue.pretty()}")
            Text("result: $outputResultText")
        }
    }
}