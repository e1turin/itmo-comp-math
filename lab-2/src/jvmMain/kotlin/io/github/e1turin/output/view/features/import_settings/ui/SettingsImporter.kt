package io.github.e1turin.output.view.features.import_settings.ui

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import io.github.e1turin.output.view.entities.repository.settings.model.SettingsRepository
import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlinx.coroutines.*
import java.io.IOException


@Composable
inline fun <reified T : Settings.Data> SettingsImporter(
    crossinline onComplete: (ImportResult<T>) -> Unit //TODO: result types
) {
    FilePicker(
        show = true,
    ) { file ->
        val handler = CoroutineExceptionHandler { _, e ->
            println("[SettingsImporter.kt]error appeared: $e")
            onComplete(ImportResult.Error(e))
        }

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch(handler) {
            val filePath = file?.path ?: throw IOException("No file selected")
            val data = SettingsRepository.loadFromFile<T>(filePath)
            onComplete(ImportResult.Complete(data))
        }
    }
}

sealed interface ImportResult<T : Settings.Data> {
    data class Complete<T : Settings.Data>(val data: T) : ImportResult<T>
    data class Error<T : Settings.Data>(val e: Throwable) : ImportResult<T>
}