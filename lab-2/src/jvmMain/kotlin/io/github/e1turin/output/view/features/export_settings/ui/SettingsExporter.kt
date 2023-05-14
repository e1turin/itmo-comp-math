package io.github.e1turin.output.view.features.export_settings.ui

import androidx.compose.runtime.Composable
import io.github.e1turin.output.view.entities.file_system.model.FileSystemAdapter
import io.github.e1turin.output.view.entities.file_system.ui.FileSelector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Composable
fun SettingsExporter(
    jsonData: String,
    onComplete: () -> Unit = {}
) {

    FileSelector(
        show = true,
    ) { file ->

        val filePath = file?.path

        if (filePath != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    supervisorScope {
                        FileSystemAdapter.saveToFile(filePath, jsonData)
                    }
                } catch (e: Exception) {
                    println("[SettingsExporter.kt]exception appeared: $e")
                }
            }
        } else {
            println("[SettingsExporter.kt]export path is null")
        }

        onComplete()
    }


}