package io.github.e1turin.output.view.entities.settings.ui.method.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.SystemSimpleIterationSettings
import io.github.e1turin.output.view.shared.lib.std.*
import io.github.e1turin.output.view.shared.ui.form.Property
import io.github.e1turin.output.view.shared.ui.range.RangePicker


@Composable
fun SystemSimpleIterationForm(
    modifier: Modifier = Modifier,
    settings: SystemSimpleIterationSettings,
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
                RangePicker(
                    range = range,
                    onMoveLeft = {
                        range = range.slideToLowestBy(0.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                    onMoveRight = {
                        range = range.slideToHighestBy(0.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                    onShrink = {
                        if (range.length > 0.2F) {
                            range = range.shrinkBy(0.1F)
                            settings.onRangeChange(range.toDoubleRange())
                        }
                    },
                    onStretch = {
                        range = range.stretchBy(0.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                )
            }
        }
    }
}


private fun calculateBoundsOfRange(middleValue: Float): ClosedFloatingPointRange<Float> =
    (middleValue - 1.5F)..(middleValue + 1.5F)
