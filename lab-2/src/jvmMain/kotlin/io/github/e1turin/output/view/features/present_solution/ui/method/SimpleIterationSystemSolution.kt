package io.github.e1turin.output.view.features.present_solution.ui.method

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.model.domain.equation.nonlinear.system.method.SimpleIterationSystemSolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.system.solver.IterativeSystemSolver
import io.github.e1turin.model.util.vectorLength
import io.github.e1turin.output.view.entities.settings.model.SystemSimpleIterationSettings
import io.github.e1turin.output.view.features.present_solution.model.SolvingResult

@Composable
fun SimpleIterationSystemSolutionPresenter(
    modifier: Modifier = Modifier,
    settings: SystemSimpleIterationSettings
) {
    val data by settings.data.subscribeAsState()

    var initialValue = data.initialValue

    val result = try {
        val method = SimpleIterationSystemSolvingMethod(
            approximationFunction = listOf(
                { hx -> hx[0] },
                { hx -> -hx[1] },
            )
        )

        val testCondition = SimpleIterationSystemSolvingMethod.testConvergenceCondition(
            range = data.range,
            jacobianMatrix = listOf(
                listOf({ 1.0 }, { -1.0 }),
                listOf({ 1.0 }, { 1.0 })
            )
        )

        var step = 0
        val solution = IterativeSystemSolver(
            method = method,
            initialApproximation = data.initialValue,
            stopCondition = { deviance -> vectorLength(deviance) < 0.001 || ++step > 10000 }
        )

    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

}