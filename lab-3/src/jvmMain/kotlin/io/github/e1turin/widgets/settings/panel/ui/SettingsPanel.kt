package io.github.e1turin.widgets.settings.panel.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.features.settings.setup.ui.SetupSettings

@Composable
fun SettingsPanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings", fontSize = 26.sp,
            modifier = Modifier.padding(5.dp)
        )
        Divider(Modifier.fillMaxWidth(0.75F), thickness = 2.dp, color = Color.DarkGray)
        SetupSettings(Modifier.fillMaxWidth())
    }
}

