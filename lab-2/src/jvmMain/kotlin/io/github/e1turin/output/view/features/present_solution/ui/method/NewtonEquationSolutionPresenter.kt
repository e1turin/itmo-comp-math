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
import io.github.e1turin.model.domain.equation.nonlinear.method.NewtonSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.solver.IterativeEquationSolver
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.shared.lib.std.pretty
import kotlin.math.abs

@Composable
fun NewtonSolvingMethodSolutionPresenter(
    modifier: Modifier = Modifier,
    settings: NewtonEquationSettings
){
    val data by settings.data.subscribeAsState()

    var initialValue: Double = data.initialValue

    val solvingResult: SolvingResult = try {
        val method = NewtonSolvingMethod(
            range = data.range,
            function = data.function!!,
//            derivative = TODO()
        )

        initialValue = NewtonSolvingMethod.initialApproximation(
            range = data.range,
            function = data.function!!
//            firstDerivative = TODO(),
//            secondDerivative = TODO()
        ).also { settings.onInitialValueChange(it) }

        var step = 0
        val solver = IterativeEquationSolver(
            method = method,
            initialApproximation = initialValue,
            stopCondition = { eps: Double -> abs(eps) < 0.001 || ++step < 10000 }
        )
        val solution = solver.solve(data.function!!)
        SolvingResult.Number(solution)
    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

    val outputText: String = when (solvingResult) {
        is SolvingResult.Error -> "⚠ " + (solvingResult.exception.message ?: "Error appeared while computing solution")
        is SolvingResult.Number -> "x= " + solvingResult.realNumber.pretty()
        is SolvingResult.Range -> "range: " + solvingResult.range.pretty()
    }


    SelectionContainer {
        Column(modifier = modifier) {
            Text("Solution")
                .also { Spacer(Modifier.size(10.dp)) }

            Text("initial value: ${initialValue.pretty()}")
            Text(outputText)
        }
    }
}