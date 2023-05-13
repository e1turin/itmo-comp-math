package io.github.e1turin.output.view.entities.file_system.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object FileSystemAdapter {

    suspend fun saveToFile(
        filePath: String,
        data: String
    ) = withContext(Dispatchers.IO) {

        val file = File(filePath)

        file.writeText(data)
    }
}

sealed interface OperationResult {
    data class Complete(val success: Boolean) : OperationResult
    data class Error(val e: Exception) : OperationResult
}