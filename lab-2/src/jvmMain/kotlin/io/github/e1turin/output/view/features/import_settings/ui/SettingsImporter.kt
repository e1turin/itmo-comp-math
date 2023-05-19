package io.github.e1turin.output.view.features.import_settings.ui

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import io.github.e1turin.output.view.entities.settings.model.Settings
import io.github.e1turin.output.view.entities.repository.settings.model.SettingsRepository
import kotlinx.coroutines.*
import java.io.IOException


@Composable
inline fun <reified T : Settings.Data> SettingsImporter(
    crossinline onComplete: (T?) -> Unit = {} //TODO: result types
) {
    FilePicker(
        show = true,
    ) { file ->

        val handler = CoroutineExceptionHandler { _, e ->
            println("[SettingsImporter.kt]error appeared: $e")
            onComplete(null)
        }

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch(handler) {
            val filePath = file?.path
            if (filePath != null) {
                val data = SettingsRepository.loadFromFile<T>(filePath)
                if (data == null) throw IOException("Incompatible settings type")
                else onComplete(data)
            } else {
                throw IOException("File was not selected")
            }
        }
    }
}