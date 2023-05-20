package io.github.e1turin.output.view.entities.settings.ui.method.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.SimpleIterationSystemSettings
import io.github.e1turin.output.view.features.export_settings.ui.SettingsExporter
import io.github.e1turin.output.view.features.import_settings.ui.SettingsImporter
import io.github.e1turin.output.view.shared.lib.std.*
import io.github.e1turin.output.view.shared.ui.form.Property
import io.github.e1turin.output.view.shared.ui.range.RangePicker


@Composable
fun SystemSimpleIterationForm(
    modifier: Modifier = Modifier,
    settings: SimpleIterationSystemSettings,
) {
    val data by settings.data.subscribeAsState()

    var initialXValueInput by remember { mutableStateOf(data.initialValue[0].toString()) }
    var initialYValueInput by remember { mutableStateOf(data.initialValue[1].toString()) }

    var showExportFileSelector by remember { mutableStateOf(false) }
    var showImportFileSelector by remember { mutableStateOf(false) }

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

                    if (newXValue.isFinite()) {
                        settings.onInitialXValueChange(newXValue)
                        settings.onXRangeChange(
                            calculateBoundsOfRange(newXValue.toFloat()).toDoubleRange()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
                .also { Spacer(Modifier.size(10.dp)) }

            Text("Value of X in use: ${data.initialValue[0].pretty()}").also {
                    Spacer(
                        Modifier.padding(5.dp).height(1.dp).fillMaxWidth(0.75F).background(Color.DarkGray)
                    )
                }
        }

        Property(title = "Inspecting X range") {
            Column {
                RangePicker(
                    range = data.range[0].toFloatRange(),
                    onMoveLeft = {
                        settings.onXRangeChange(data.range[0].slideToLowestBy(0.1))
                    },
                    onMoveRight = {
                        settings.onXRangeChange(data.range[0].slideToHighestBy(0.1))
                    },
                    onShrink = {
                        if (data.range[0].length > 0.2F) {
                            settings.onXRangeChange(data.range[0].shrinkBy(0.1))
                        }
                    },
                    onStretch = {
                        settings.onXRangeChange(data.range[0].stretchBy(0.1))
                    },
                )
            }
        }

        Spacer(Modifier.padding(5.dp).height(3.dp).fillMaxWidth().background(Color.DarkGray))

        Property(title = "Initial Y value") {
            TextField(
                value = initialYValueInput,
                onValueChange = { newYValueString ->
                    initialYValueInput = newYValueString
                    val newYValue: Double = initialYValueInput.toDoubleOrNull() ?: data.initialValue[1]

                    if (newYValue.isFinite()) {
                        settings.onInitialYValueChange(newYValue)

                        settings.onYRangeChange(
                            calculateBoundsOfRange(newYValue.toFloat()).toDoubleRange()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
                .also { Spacer(Modifier.size(10.dp)) }

            Text("Value of Y in use: ${data.initialValue[1].pretty()}")
                .also {
                    Spacer(
                        Modifier.padding(5.dp).height(1.dp).fillMaxWidth(0.75F).background(Color.DarkGray)
                    )
                }
        }

        Property(title = "Inspecting Y range") {
            Column {
                RangePicker(
                    range = data.range[1].toFloatRange(),
                    onMoveLeft = {
                        settings.onYRangeChange(data.range[1].slideToLowestBy(0.1))
                    },
                    onMoveRight = {
                        settings.onYRangeChange(data.range[1].slideToHighestBy(0.1))
                    },
                    onShrink = {
                        if (data.range[1].length > 0.2F) {
                            settings.onYRangeChange(data.range[1].shrinkBy(0.1))
                        }
                    },
                    onStretch = {
                        settings.onYRangeChange(data.range[1].stretchBy(0.1))
                    },
                )
            }
        }

        Spacer(Modifier.weight(1F))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                showExportFileSelector = true
            }) {
                Text("Export settings")
            }.also {
                if (showExportFileSelector) {
                    SettingsExporter(
                        data = settings
                    ) {
                        showExportFileSelector = false
                    }
                }
            }

            Button(onClick = {
                showImportFileSelector = true
            }) {
                Text("Import settings")
            }.also {
                if (showImportFileSelector) {
                    SettingsImporter<SimpleIterationSystemSettings.SystemSimpleIterationData> { data ->
                        if (data != null) {
                            // XXX: may be Dispatchers.Main is needed?
                            settings.onInitialValueChange(data.initialValue)
                            settings.onRangeChange(data.range)
                            println("[SSIForm]$data")
                        }
                        showImportFileSelector = false
                    }
                }
            }

        }
    }
}


