package io.github.e1turin.pages

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.entities.finiteDifference.FiniteDifferencesStore
import io.github.e1turin.entities.interpolations.Interpolation
import io.github.e1turin.entities.interpolations.InterpolationsStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.entities.repository.FDRepository
import io.github.e1turin.entities.repository.JsonPointsRepository
import io.github.e1turin.features.points.calculate.InputData
import io.github.e1turin.shared.config.samples.availableFunctions
import io.github.e1turin.shared.config.samples.availableInterpolations
import io.github.e1turin.shared.lib.std.length
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

                val interpolations = mutableListOf<Interpolation>()
                availableInterpolations.forEach {
                    try {
                        it.fit(points)
                        interpolations.add(it)
                    } catch (e: Exception) {
                        println("[MainActivity.kt]error: ${e.message}")
                    }
                }

                InterpolationsStore.onAllNewInterpolationsSet(interpolations)
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

    fun generatePoints(inputData: InputData) {
        scope.launch {
            try {
                val function = availableFunctions[inputData.functionLabel]
                require(function != null) { "Function must not be null" }

                val step = inputData.range.length / inputData.divisions

                val points = mutableListOf<Point>()
                var currX = inputData.range.start
                repeat(inputData.divisions) {
                    points.add(Point(currX, function(currX)))
                    currX += step
                }

                PointsStore.onAllPointsAppend(points)
            } catch (e: Exception) {
                _message.value = "Error while computing points"
                println("[MainActivity.kt]error: ${e.message}")
            }
        }

    }

}