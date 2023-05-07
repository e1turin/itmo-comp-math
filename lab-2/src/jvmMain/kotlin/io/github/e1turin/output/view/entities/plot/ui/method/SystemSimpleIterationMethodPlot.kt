package io.github.e1turin.output.view.entities.plot.ui.method

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.plot.ui.SystemPlot2D
import io.github.e1turin.output.view.entities.settings.model.SystemSimpleIterationSettings
import io.github.e1turin.output.view.shared.lib.std.toFloatRange

@Composable
fun SystemSimpleIterationMethodPlot(
    modifier: Modifier = Modifier,
    settings: SystemSimpleIterationSettings
) {
    val data by settings.data.subscribeAsState()

    SystemPlot2D(
        modifier = modifier,
        inspectingRange = data.range.toFloatRange(),
        system = data.system!!
    )
}