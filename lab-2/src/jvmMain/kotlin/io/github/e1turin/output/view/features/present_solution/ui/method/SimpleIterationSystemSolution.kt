package io.github.e1turin.output.view.features.present_solution.ui.method

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.model.domain.equation.nonlinear.system.method.SimpleIterationSystemSolvingMethod
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
                { hx -> hx[0] - 1 },
                { hx -> hx[1] + 1 },
            )
        )

    } catch (e: Exception) {
        SolvingResult.Error(e)
    }

}