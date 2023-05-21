package io.github.e1turin.output.view.features.draw_plot.ui.method.system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.SISystemSettings
import io.github.e1turin.output.view.features.draw_plot.ui.SystemPlot2D
import io.github.e1turin.output.view.shared.lib.std.toFloatRanges

/**
 * Function for plotting simple iteration method for solving systems
 */
@Composable
fun SISystemMethodPlot(
    modifier: Modifier = Modifier,
    settings: SISystemSettings
) {
    val data by settings.data.subscribeAsState()

    SystemPlot2D(
        modifier = modifier,
        inspectingZone = data.range.toFloatRanges(),
        system = data.system!!
    )
}