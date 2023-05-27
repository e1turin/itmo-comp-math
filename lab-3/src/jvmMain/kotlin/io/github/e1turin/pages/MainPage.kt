package io.github.e1turin.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.widgets.settings.panel.ui.SettingsPanel


@Composable
fun MainPage() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SettingsPanel(Modifier.weight(1F))
    }
}
