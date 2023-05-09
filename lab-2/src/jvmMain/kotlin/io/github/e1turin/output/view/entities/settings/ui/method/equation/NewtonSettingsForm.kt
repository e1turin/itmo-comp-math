package io.github.e1turin.output.view.entities.settings.ui.method.equation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.shared.lib.std.*
import io.github.e1turin.output.view.shared.ui.form.Property
import io.github.e1turin.output.view.shared.ui.range.RangePicker


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
                modifier = Modifier.fillMaxWidth(),
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
//                RangeSlider(
//                    modifier = Modifier,
//                    value = range,
//                    onValueChange = { newRange: ClosedFloatingPointRange<Float> ->
//                        range = newRange
//                    },
//                    valueRange = boundsOfInspection,
//                    onValueChangeFinished = {
//                        settings.onRangeChange(range.toDoubleRange())
//                    }
//                )
//                Text("start: ${range.start.pretty()}")
//                Text("end:   ${range.endInclusive.pretty()}")
            }

        }

    }

}


private fun calculateBoundsOfRange(middleValue: Float): ClosedFloatingPointRange<Float> =
    (middleValue - 1.5F)..(middleValue + 1.5F)
