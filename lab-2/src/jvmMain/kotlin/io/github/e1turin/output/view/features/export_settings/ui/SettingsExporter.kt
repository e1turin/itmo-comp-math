package io.github.e1turin.output.view.features.export_settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.github.e1turin.output.view.entities.file_system.model.FileSystemAdapter
import io.github.e1turin.output.view.entities.file_system.ui.FileSelector
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Composable
fun SettingsExporter(
    jsonData: String,
    onComplete: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    FileSelector(
        show = true,
        onFileSelected = { file ->
            val filePath = file?.path

            if (filePath != null) {
                coroutineScope.launch {
                    try {
                        supervisorScope {
                            FileSystemAdapter.saveToFile(filePath, jsonData)
                        }
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }

            onComplete()
        }
    )


}