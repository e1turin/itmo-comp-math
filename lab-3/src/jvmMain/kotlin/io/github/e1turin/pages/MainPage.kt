package io.github.e1turin.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.widgets.settings.panel.ui.SettingsPanel
import io.github.e1turin.widgets.solution.panel.ui.SolutionPanel


@Composable
fun MainPage() {
    Column(
        modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SettingsPanel(
            Modifier.weight(1F)
        )

        SolutionPanel(
            Modifier
        )
    }
}
