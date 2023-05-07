package io.github.e1turin.output.view.pages.graph.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.form.model.Settings
import io.github.e1turin.output.view.entities.form.ui.SettingsPanel
import io.github.e1turin.output.view.entities.plot.ui.FunctionPlot1
import io.github.e1turin.output.view.shared.lib.plot.gu

@Composable
fun GraphDrawing() {
    var settings by remember { mutableStateOf(Settings()) }

    Row(modifier = Modifier.padding(5.dp)) {
        FunctionPlot1(
            inspectingRange = settings.functionRangeStart.gu..settings.functionRangeEnd.gu,
            step = settings.graphGridStep.gu,
            gridDensity = settings.graphDensity.toFloat(),
        ) { x -> x }

        SettingsPanel(settings) { settings = it }
    }
}
