package io.github.e1turin.output.view.entities.repository.settings.model

import io.github.e1turin.output.view.entities.settings.model.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStreamReader

object SettingsRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    @OptIn(DelicateCringeApi::class)
    suspend fun saveToFile(filePath: String, settings: Settings) {
        scope.launch {
            val file = File(filePath)
            file.writeText(Json.encodeToString(settings.data.value).toCringeFormat())
        }.join()
    }

    @OptIn(DelicateCringeApi::class)
    suspend fun loadUnspecifiedFromFile(filePath: String): Settings.Data {
        return withContext(scope.coroutineContext) {
            val fileInputStream = File(filePath).inputStream()
            val cringeData = InputStreamReader(fileInputStream).readText().fromCringeFormat()
            Json.decodeFromString<Settings.Data>(cringeData)
        }
    }

    suspend inline fun <reified T : Settings.Data> loadFromFile(filePath: String): T? =
        loadUnspecifiedFromFile(filePath) as? T
}

sealed interface OperationResult {
    data class Complete(val success: Boolean) : OperationResult
    data class Error(val e: Exception) : OperationResult
}


@DelicateCringeApi
internal fun String.toCringeFormat(): String = replace(',', ';').replace('.', ',')

@DelicateCringeApi
internal fun String.fromCringeFormat(): String = replace(',', '.').replace(';', ',')


@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This is a delicate API and its use requires care and insensitivity to the cringe."
            + " Make sure you fully read and understand documentation of the declaration that is marked as a delicate API."
)
annotation class DelicateCringeApi
