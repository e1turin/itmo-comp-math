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
import io.github.e1turin.model.domain.equation.nonlinear.method.NewtonSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.solver.IterativeEquationSolver
import io.github.e1turin.output.view.entities.settings.model.ChordEquationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.features.present_solution.model.toResultString
import io.github.e1turin.output.view.shared.lib.std.pretty
import kotlin.math.abs

@Composable
fun ChordEquationSolution(
    modifier: Modifier = Modifier,
    settings: ChordEquationSettings
) {
    val data by settings.data.subscribeAsState()

    var initialValue: Double = data.initialValue

    val solvingResult: SolvingResult = try {
        val method = NewtonSolvingMethod(
            range = data.range,
            function = data.function!!,
        )

        initialValue = NewtonSolvingMethod.initialApproximation(
            range = data.range,
            function = data.function!!
        ).also { settings.onInitialValueChange(it) }

        var step = 0
        val solver = IterativeEquationSolver(
            method = method,
            initialApproximation = initialValue,
            stopCondition = { eps: Double -> abs(eps) < 0.001 || ++step > 10000 }
        )

        val solution = solver.solve(data.function!!)

        SolvingResult.Number(solution)
    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

    val outputText: String = solvingResult.toResultString()

    SelectionContainer {
        Column(modifier = modifier) {
            Text("Solution")
                .also { Spacer(Modifier.size(10.dp)) }

            Text("initial value: ${initialValue.pretty()}")
            Text(outputText)
        }
    }
}