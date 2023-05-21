package io.github.e1turin.output.view.features.export_settings.ui

import androidx.compose.runtime.Composable
import io.github.e1turin.output.view.entities.file_system.ui.FileSelector
import io.github.e1turin.output.view.entities.repository.settings.model.SettingsRepository
import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlinx.coroutines.*

@Composable
fun SettingsExporter(
    data: Settings,
    onComplete: (ExportResult) -> Unit = {}
) {
    FileSelector(
        show = true,
    ) { file ->
        val handler = CoroutineExceptionHandler { _, e ->
            onComplete(ExportResult.Error(e))
        }

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch(handler) {
            val filePath = file?.path ?: throw Exception("No file selected")
            SettingsRepository.saveToFile(filePath, data)
            onComplete(ExportResult.Complete)
        }
    }
}

sealed interface ExportResult {
    object Complete : ExportResult
    data class Error(val e: Throwable) : ExportResult
}