package io.github.e1turin.output.view.pages.graph.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.equation.model.function
import io.github.e1turin.output.view.entities.form.model.Settings
import io.github.e1turin.output.view.entities.form.ui.SettingsPanel
import io.github.e1turin.output.view.entities.graph.ui.FunctionGraph
import io.github.e1turin.output.view.entities.graph.ui.gu

@Composable
fun GraphDrawing() {
    var settings by remember { mutableStateOf(Settings()) }

    Row(modifier = Modifier.padding(5.dp)) {
        FunctionGraph(
            inspectingRange = settings.functionRangeStart.gu..settings.functionRangeEnd.gu,
            step = settings.graphGridStep.gu,
            density = settings.graphDensity.toFloat(),
        ) { x -> function(x) }

        SettingsPanel(settings) { settings = it }
    }
}
