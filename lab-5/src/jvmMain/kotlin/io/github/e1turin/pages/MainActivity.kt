package io.github.e1turin.pages

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.entities.finiteDifference.FiniteDifferencesStore
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.entities.repository.FDRepository
import io.github.e1turin.entities.repository.JsonPointsRepository
import io.github.e1turin.shared.lib.std.pretty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity(
    private val scope: CoroutineScope
) {

    private val _message = mutableStateOf("")
    public val message: State<String> = this._message


    fun hideErrorMessage() {
        this._message.value = ""
    }

    fun loadPointsFromFile(file: File) {
        scope.launch {
            try {
                val points = withContext(Dispatchers.IO) { JsonPointsRepository.loadFrom(file) }
                PointsStore.onPointsClean()
                PointsStore.onAllPointsAppend(points)
            } catch (e: Exception) {
                _message.value = "Error while decoding file"
                println("[MainActivity.kt]error: ${e.message}")
            }
        }
    }

    fun savePointsToFile(file: File) {
        scope.launch {
            try {
                JsonPointsRepository.saveTo(file, PointsStore.points.value)
            } catch (e: Exception) {
                println("[MainActivity.kt]error: ${e.message}")
                _message.value = "Error while writing file"
            }
        }
    }

    fun computeFD() {
        scope.launch {
            try {
                val points by PointsStore.points
                val s = mutableSetOf<String>()
                for (p in points) {
                    val x = p.x.pretty()
                    if (s.contains(x)) {
                        _message.value = "Different points have one x=$x"
                        return@launch
                    } else {
                        s.add(x)
                    }
                }

                FiniteDifferencesStore.calculateNewFiniteDifferences(points)
            } catch (e: Exception) {
                println("[MainActivity.kt]error: ${e.message}")
                _message.value = "Error while computations"
            }
        }
    }

    fun saveFDPrintToFile(file: File) {
        scope.launch {
            try {
                val data = FiniteDifferencesStore.prettyPrintFD()
                FDRepository.saveTo(file, data)
            } catch (e: Exception) {
                _message.value = "Error while saving to file"
                println("[MainActivity.kt]error: ${e.message}")
            }
        }
    }


}