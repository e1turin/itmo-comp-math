package io.github.e1turin.output.view.entities.settings.ui.method.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    var xRange by remember { mutableStateOf(data.range[0].toFloatRange()) }
    var yRange by remember { mutableStateOf(data.range[1].toFloatRange()) }

    var initialXValueInput by remember { mutableStateOf(data.initialValue[0].toString()) }
    var initialYValueInput by remember { mutableStateOf(data.initialValue[1].toString()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Property(title = "Initial X value") {
            TextField(
                value = initialXValueInput,
                onValueChange = { newXValueString ->
                    initialXValueInput = newXValueString

                    val newXValue: Double = initialXValueInput.toDoubleOrNull() ?: data.initialValue[0]

                    settings.onInitialXValueChange(newXValue)

                    xRange = calculateBoundsOfRange(newXValue.toFloat())

                    settings.onXRangeChange(xRange.toDoubleRange())
                },
                modifier = Modifier,
                maxLines = 1
            )
        }

        Property(title = "Inspecting X range") {
            Column {
                RangePicker(
                    range = xRange,
                    onMoveLeft = {
                        xRange = xRange.slideToLowestBy(0.1F)
                        settings.onXRangeChange(xRange.toDoubleRange())
                    },
                    onMoveRight = {
                        xRange = xRange.slideToHighestBy(0.1F)
                        settings.onXRangeChange(xRange.toDoubleRange())
                    },
                    onShrink = {
                        if (xRange.length > 0.2F) {
                            xRange = xRange.shrinkBy(0.1F)
                            settings.onXRangeChange(xRange.toDoubleRange())
                        }
                    },
                    onStretch = {
                        xRange = xRange.stretchBy(0.1F)
                        settings.onXRangeChange(xRange.toDoubleRange())
                    },
                )
            }
        }

        Spacer(Modifier.padding(5.dp).height(1.dp).fillMaxWidth().background(Color.DarkGray))

        Property(title = "Initial Y value") {
            TextField(
                value = initialYValueInput,
                onValueChange = { newYValueString ->
                    initialYValueInput = newYValueString

                    val newYValue: Double = initialYValueInput.toDoubleOrNull() ?: data.initialValue[1]

                    settings.onInitialYValueChange(newYValue)

                    yRange = calculateBoundsOfRange(newYValue.toFloat())

                    settings.onYRangeChange(yRange.toDoubleRange())
                },
                modifier = Modifier,
                maxLines = 1
            )
        }

        Property(title = "Inspecting Y range") {
            Column {
                RangePicker(
                    range = yRange,
                    onMoveLeft = {
                        yRange = yRange.slideToLowestBy(0.1F)
                        settings.onYRangeChange(yRange.toDoubleRange())
                    },
                    onMoveRight = {
                        yRange = yRange.slideToHighestBy(0.1F)
                        settings.onYRangeChange(yRange.toDoubleRange())
                    },
                    onShrink = {
                        if (yRange.length > 0.2F) {
                            yRange = yRange.shrinkBy(0.1F)
                            settings.onYRangeChange(yRange.toDoubleRange())
                        }
                    },
                    onStretch = {
                        yRange = yRange.stretchBy(0.1F)
                        settings.onYRangeChange(yRange.toDoubleRange())
                    },
                )
            }
        }
    }
}


private fun calculateBoundsOfRange(middleValue: Float): ClosedFloatingPointRange<Float> =
    (middleValue - 1.5F)..(middleValue + 1.5F)
