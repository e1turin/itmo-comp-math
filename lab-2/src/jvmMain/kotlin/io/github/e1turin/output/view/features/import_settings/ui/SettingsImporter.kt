package io.github.e1turin.output.view.features.import_settings.ui

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import io.github.e1turin.output.view.entities.settings.model.NewtonEquationSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun SettingsImporter(
    onComplete: (NewtonEquationSettings.NewtonData?) -> Unit = {}
) {
    FilePicker(
        show = true,
    ) { file ->
        val filePath = file?.path

        if (filePath != null) {
            CoroutineScope(Dispatchers.IO).launch {
                var data: NewtonEquationSettings.NewtonData? = null
                try {
                    supervisorScope {
                        val fileInputStream = File(filePath).inputStream()
                        data = Json.decodeFromStream<NewtonEquationSettings.NewtonData>(fileInputStream)
                    }
                } catch (e: Exception) {
                    println("[SettingsImporter.kt]exception appeared: $e")
                } finally {
                    onComplete(data)
                }
            }
        } else {
            onComplete(null)
        }
    }
}