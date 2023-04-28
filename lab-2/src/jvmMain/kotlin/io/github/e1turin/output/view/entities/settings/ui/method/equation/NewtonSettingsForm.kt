package io.github.e1turin.output.view.entities.settings.ui.method.equation

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.shared.lib.compose.toDoubleRange
import io.github.e1turin.output.view.shared.lib.compose.toFloatRange


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun NewtonSettingsForm(
    modifier: Modifier = Modifier,
    settings: NewtonEquationSettings,
) {
    val data by settings.data.subscribeAsState()
    var boundsOfInspection by remember {
        mutableStateOf(calculateBoundsOfRange(data.initialValue.toFloat()))
    }
    var range by remember { mutableStateOf(data.range.toFloatRange()) }
    var initialValueInput by remember { mutableStateOf(data.initialValue.toString()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Property(title = "Initial value") {
            TextField(
                value = initialValueInput,
                onValueChange = { newValueString ->
                    initialValueInput = newValueString
                    val newValue = initialValueInput.toDoubleOrNull() ?: data.initialValue
                    settings.onInitialValueChange(newValue)
                    boundsOfInspection = calculateBoundsOfRange(newValue.toFloat())
                    range = boundsOfInspection
                    settings.onRangeChange(range.toDoubleRange())
                },
                modifier = Modifier,
                maxLines = 1
            )
        }

        Property(title = "Inspecting range") {
            Column {
                RangeSlider(
                    modifier = Modifier,
                    value = range,
                    onValueChange = { newRange: ClosedFloatingPointRange<Float> ->
                        range = newRange
                    },
                    valueRange = boundsOfInspection,
                    onValueChangeFinished = {
                        settings.onRangeChange(range.toDoubleRange())
                    }
                )
                Text("start: ${range.start}")
                Text("end: ${range.endInclusive}")
            }

        }

    }

}

@Composable
private fun Property(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(title)
            .also { Spacer(Modifier.size(10.dp)) }

        content()
    }
}

private fun calculateBoundsOfRange(middleValue: Float): ClosedFloatingPointRange<Float> =
    (middleValue - 1.5F)..(middleValue + 1.5F)
