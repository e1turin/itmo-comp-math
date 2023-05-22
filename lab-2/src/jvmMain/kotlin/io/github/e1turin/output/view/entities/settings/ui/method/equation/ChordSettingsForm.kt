package io.github.e1turin.output.view.entities.settings.ui.method.equation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.settings.model.ChordEquationSettings
import io.github.e1turin.output.view.features.export_settings.ui.ExportResult
import io.github.e1turin.output.view.features.export_settings.ui.SettingsExporter
import io.github.e1turin.output.view.features.import_settings.ui.ImportResult
import io.github.e1turin.output.view.features.import_settings.ui.SettingsImporter
import io.github.e1turin.output.view.shared.config.isReasonable
import io.github.e1turin.output.view.shared.config.msToMessageDisappear
import io.github.e1turin.output.view.shared.lib.std.*
import io.github.e1turin.output.view.shared.ui.form.Property
import io.github.e1turin.output.view.shared.ui.range.RangePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
internal fun ChordSettingsForm(
    modifier: Modifier = Modifier,
    settings: ChordEquationSettings,
) {
    val coroutineScope = rememberCoroutineScope()
    val data by settings.data.subscribeAsState()

    var showExportFileSelector by remember { mutableStateOf(false) }
    var showImportFileSelector by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Property(title = "Inspecting range") {
            Column {
                RangePicker(
                    range = data.range.toFloatRange(),
                    onMoveLeft = {
                        settings.onRangeChange(data.range.slideToLowestBy(0.1))
                    },
                    onMoveRight = {
                        settings.onRangeChange(data.range.slideToHighestBy(0.1))
                    },
                    onShrink = {
                        if (data.range.length > 0.2F) {
                            settings.onRangeChange(data.range.shrinkBy(0.1))
                        }
                    },
                    onStretch = {
                        settings.onRangeChange(data.range.stretchBy(0.1))
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
                    ) { result ->
                        message = when (result) {
                            is ExportResult.Complete -> "✅ Settings are exported successfully"
                            is ExportResult.Error ->
                                "⛔ ${result.e.message ?: "Error while exporting"}"
                        }
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
                    SettingsImporter<ChordEquationSettings.ChordData> { result ->
                        message = when (result) {
                            is ImportResult.Complete -> {
                                if (result.data.range.isReasonable()) {
                                    settings.onRangeChange(result.data.range)
                                    "✅ Settings are imported successfully"
                                } else {
                                    "⚠ Settings are beyond reasonable"
                                }
                            }

                            is ImportResult.Error -> {
                                result.e.message ?: "Error while importing"
                            }
                        }
                        showImportFileSelector = false
                    }

                }
            }

        }

        if (message.isNotBlank()) {
            SelectionContainer {
                Text(message)
            }
            coroutineScope.launch {
                delay(msToMessageDisappear)
                message = ""
            }
        }

    }

}
