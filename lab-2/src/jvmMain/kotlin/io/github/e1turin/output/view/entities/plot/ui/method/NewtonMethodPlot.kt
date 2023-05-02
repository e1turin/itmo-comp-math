package io.github.e1turin.output.view.entities.plot.ui.method

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.plot.ui.FunctionPlot
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.shared.lib.plot.toGu

@Composable
fun NewtonMethodPlot(
    modifier: Modifier = Modifier,
    settings: NewtonEquationSettings
) {
    val data by settings.data.subscribeAsState()

    FunctionPlot(
        modifier = modifier,
        inspectingRange = data.range.toGu(),
    ) {
        val f = data.function ?: throw IllegalArgumentException("Function to draw must not be null")
        f(it.toDouble()).toFloat()
    }

//    SimpleFunctionPlot(
//        modifier = modifier,
//        inspectingRange = data.range.toFloatRange(),
//        step = 0.01F,
//        gridDensity = 10F,
//        scale = data.scale.toFloat(),
//        translateTopLeft = data.translate,
//    ) {
//        val f = data.function ?: throw IllegalArgumentException("Function to draw must not be null")
//        f(it.toDouble()).toFloat()
//    }

}