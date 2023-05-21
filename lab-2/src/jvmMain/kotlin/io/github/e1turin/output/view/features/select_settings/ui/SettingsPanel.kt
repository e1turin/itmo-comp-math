package io.github.e1turin.output.view.features.select_settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.entities.settings.ui.method.equation.ChordSettingsForm
import io.github.e1turin.output.view.entities.settings.ui.method.equation.NewtonSettingsForm
import io.github.e1turin.output.view.entities.settings.ui.method.equation.SIEquationSettingsForm
import io.github.e1turin.output.view.entities.settings.ui.method.system.SISystemSettingsForm

@Composable
fun SettingsPanel(
    modifier: Modifier = Modifier,
    model: Settings,
) {

    Column(
        modifier = modifier
            .background(Color.LightGray, RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (model) {
            is NewtonEquationSettings -> NewtonSettingsForm(modifier = Modifier, settings = model)
            is SIEquationSettings -> SIEquationSettingsForm(modifier = Modifier, settings = model)
            is ChordEquationSettings -> ChordSettingsForm(modifier = Modifier, settings = model)

            is SISystemSettings -> SISystemSettingsForm(modifier = Modifier, settings = model)

            is DefaultSettings -> Box(contentAlignment = Alignment.Center) {
                Text("Select a task first")
            }

            DefaultEquationSettings, DefaultSystemSettings -> {/* impossible way due to previous case */
            }

        }
    }
}
