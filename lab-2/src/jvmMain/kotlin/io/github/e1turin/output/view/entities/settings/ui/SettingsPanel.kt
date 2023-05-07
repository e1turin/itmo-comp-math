package io.github.e1turin.output.view.entities.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.entities.settings.ui.method.equation.NewtonSettingsForm
import io.github.e1turin.output.view.entities.settings.ui.method.system.SystemSimpleIterationForm

@Composable
fun SettingsPanel(
    modifier: Modifier = Modifier,
    model: Settings,
) {
    Box(
        modifier = modifier
            .background(Color.LightGray, RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentAlignment = Alignment.TopStart
    ) {
        when (model) {
            is NewtonEquationSettings -> NewtonSettingsForm(modifier = Modifier, settings = model)
            is SystemSimpleIterationSettings -> SystemSimpleIterationForm(modifier = Modifier, settings = model)
            //TODO: another methods

            is DefaultSettings -> Box(contentAlignment = Alignment.Center) {
                Text("Select a task first")
            }

            DefaultEquationSettings, DefaultSystemSettings -> {/* impossible way due to previous case */
            }

        }
    }
}
