package io.github.e1turin.output.view.entities.settings_repository.model

import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

object SettingsRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun saveToFile(filePath: String, settings: Settings) {
        scope.launch {
            val file = File(filePath)
            file.writeText(Json.encodeToString(settings.data.value))
        }.join()
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun loadUnspecifiedFromFile(filePath: String): Settings.Data {
        return withContext(scope.coroutineContext) {
            val fileInputStream = File(filePath).inputStream()
            Json.decodeFromStream<Settings.Data>(fileInputStream)
        }
    }

    suspend inline fun <reified T : Settings.Data> loadFromFile(filePath: String): T? =
        loadUnspecifiedFromFile(filePath) as? T
}

sealed interface OperationResult {
    data class Complete(val success: Boolean) : OperationResult
    data class Error(val e: Exception) : OperationResult
}