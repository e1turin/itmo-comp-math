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
import io.github.e1turin.model.domain.equation.nonlinear.method.ChordSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.solver.TelescopicEquationSolver
import io.github.e1turin.output.view.entities.settings.model.ChordEquationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult
import io.github.e1turin.output.view.features.present_solution.model.toResultString
import io.github.e1turin.output.view.shared.lib.std.length
import io.github.e1turin.output.view.shared.lib.std.prettyRange

@Composable
fun ChordEquationSolution(
    modifier: Modifier = Modifier,
    settings: ChordEquationSettings
) {
    val data by settings.data.subscribeAsState()

    val solvingResult: SolvingResult = try {
        val method = ChordSolvingMethod(
            function = data.function!!,
        )

        var step = 0
        val solver = TelescopicEquationSolver(
            method = method,
            stopCondition = { approxRange: ClosedRange<Double> ->
                approxRange.length < 0.001 || ++step > 10000
            },
            initialRange = data.range
        )

        val solution = solver.solve(data.function!!)

        SolvingResult.Number(solution)
    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

    val outputResultText: String = solvingResult.toResultString()

    SelectionContainer {
        Column(modifier = modifier) {
            Text("Solution")
                .also { Spacer(Modifier.size(10.dp)) }

            Text("initial range: ${data.range.prettyRange()}")
            Text(outputResultText)
        }
    }
}