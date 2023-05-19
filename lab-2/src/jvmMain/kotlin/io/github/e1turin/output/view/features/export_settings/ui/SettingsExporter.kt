package io.github.e1turin.output.view.features.export_settings.ui

import androidx.compose.runtime.Composable
import io.github.e1turin.output.view.entities.repository.settings.model.SettingsRepository
import io.github.e1turin.output.view.entities.file_system.ui.FileSelector
import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlinx.coroutines.*

@Composable
fun SettingsExporter(
    data: Settings,
    onComplete: () -> Unit = {}
) {

    FileSelector(
        show = true,
    ) { file ->

        val filePath = file?.path

        try {
            if (filePath != null) {
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    SettingsRepository.saveToFile(filePath, data)
                }
            } else {
                println("[SettingsExporter.kt]export path is null")
            }
        } catch (e: Exception) {
            println("[SettingsExporter.kt]exception appeared: $e")
        } finally {
            onComplete() //TODO: Return result type
        }
    }


}