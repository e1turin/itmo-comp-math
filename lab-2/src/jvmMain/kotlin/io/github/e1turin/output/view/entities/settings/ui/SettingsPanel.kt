package io.github.e1turin.output.view.entities.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.entities.settings.ui.method.equation.NewtonSettingsForm

@Composable
fun SettingsPanel(
    modifier: Modifier = Modifier,
    model: Settings,
) {
    Box(
        modifier = modifier
            .background(Color.LightGray, RoundedCornerShape(10.dp))
//            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
        ,
        contentAlignment = Alignment.TopStart
    ) {
        when (model) {
            is NewtonEquationSettings -> NewtonSettingsForm(modifier = Modifier, settings = model)
            is SystemSimpleIterationSettings -> SystemSimpleIterationProperties(model)
            //TODO: another methods

            is DefaultSettings -> {
                Text("Select a task first")
            }

            DefaultEquationSettings, DefaultSystemSettings -> {/* impossible way due to previous case */
            }

        }
    }
}


@Composable
internal fun SystemSimpleIterationProperties(settings: SystemSimpleIterationSettings) {
    val data = settings.data.subscribeAsState()

    Text("System Simple Iteration Settings")
}
