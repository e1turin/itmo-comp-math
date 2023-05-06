package io.github.e1turin.output.view.entities.settings.ui.method.equation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import io.github.e1turin.output.view.shared.lib.std.*
import io.github.e1turin.output.view.shared.ui.range.RangePicker


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
                RangePicker(
                    range = range,
                    onMoveLeft = {
                        range = range.slideToLowestBy(.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                    onMoveRight = {
                        range = range.slideToHighestBy(.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                    onShrink = {
                        range = range.shrinkBy(.1F)
                        settings.onRangeChange(range.toDoubleRange())
                    },
                    onStretch = {
                        range = range.stretchBy(.1F)
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

/*
        Property(title = "scale") {
            Column {
                Slider(
                    modifier = Modifier,
                    value = data.scale.toFloat(),
                    valueRange = 0F..500F,
                    onValueChange = {
                        settings.onScaleValueChange(it.toDouble())
                    }
                )
                Text("scale: ${data.scale}")
            }
        }
*/
//        Property(title = "translate x") {
//            Column {
//                Slider(
//                    modifier = Modifier,
//                    value = data.translate.offsetX.toFloat(),
//                    valueRange = range,
//                    onValueChange = {
//                        settings.onTranslateXChange(it.toDouble())
//                    }
//                )
//            }
//        }
//
//        Property(title = "translate y") {
//            Column {
//                Slider(
//                    modifier = Modifier,
//                    value = data.translate.offsetY.toFloat(),
//                    valueRange = range,
//                    onValueChange = {
//                        settings.onTranslateYChange(it.toDouble())
//                    }
//                )
//            }
//        }

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
