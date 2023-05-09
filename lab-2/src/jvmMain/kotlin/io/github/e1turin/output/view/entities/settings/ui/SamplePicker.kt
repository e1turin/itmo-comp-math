package io.github.e1turin.output.view.entities.settings.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.output.view.entities.settings.model.DefaultSettings
import io.github.e1turin.output.view.entities.settings.model.EquationSettings
import io.github.e1turin.output.view.entities.settings.model.Settings
import io.github.e1turin.output.view.entities.settings.model.SystemSettings
import kotlin.math.pow
import kotlin.math.sin

@Composable
fun SamplePicker(
    modifier: Modifier = Modifier,
    model: Settings
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (model) {
            is DefaultSettings -> Box(
                contentAlignment = Alignment.Center
            ) {
                Text("Select a task first")
            }

            is EquationSettings -> EquationPicker(settings = model)
            is SystemSettings -> SystemPicker(settings = model)
        }
    }
}

@Composable
private fun EquationPicker(
    modifier: Modifier = Modifier,
    settings: EquationSettings
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Selection(description = "x^3/4") {
            settings.onEquationSelect { x ->
                x.pow(3.0) / 4
            }
        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection(description = "x² + 2x + 1 = 0") {
            settings.onEquationSelect { x ->
                x.pow(2.0) + 2 * x + 1
            }
        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection(description = "sin(x)") {
            settings.onEquationSelect { x ->
                sin(x)
            }
        }
    }
}

@Composable
private fun SystemPicker(
    modifier: Modifier = Modifier, settings: SystemSettings
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Selection("{ x + 2y + 1 = 0,\n 2x - y + 1 = 0 }") {
            settings.onSystemSelect(
                listOf(
                    { x -> x[0] + 2 * x[1] + 1 },
                    { x -> 2 * x[0] - x[1] },
                )
            )
        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection("{x[0]^3.0 - 3*x[0] + x[1]^2.0 - 3},\n{x[0]^2.0 - x[1]^2.0/4 - 1}") {
            settings.onSystemSelect(
                listOf(
                    { x -> x[0].pow(3.0) - 3 * x[0] + x[1].pow(2.0) - 3 },
                    { x -> x[0].pow(2.0) - x[1].pow(2.0)/4 - 1 }
                )
            )
        }
    }
}


@Composable
private fun Selection(description: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(onClick = onClick)
            .border(1.dp, Color.Black)
            .padding(10.dp),
        text = description,
        fontSize = 20.sp,
    )
}