package io.github.e1turin.pages.main

import androidx.compose.runtime.getValue
import io.github.e1turin.entities.approximation.Approximation
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.entities.reposotory.JsonPointsRepository
import io.github.e1turin.shared.config.definedApproximations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity(private val scope: CoroutineScope) {

    fun loadPointsFromFile(file: File) {
        scope.launch {
            try {
                val points = withContext(Dispatchers.IO) { JsonPointsRepository.loadFrom(file) }
                PointStore.onPointsClean()
                PointStore.onAllPointsAppend(points)
            } catch (e: Exception) {
                println("[MainActivity.kt]error: ${e.message}")
            }
        }
    }

    fun savePointsToFile(file: File) {
        scope.launch {
            try {
                JsonPointsRepository.saveTo(file, PointStore.points.value)
            } catch (e: Exception) {
                println("[MainActivity.kt]error: ${e.message}")
            }
        }
    }

    fun calculateNewApproximations() {
        scope.launch(Dispatchers.Default) {
            val points by PointStore.points
            val x = DoubleArray(points.size)
            val y = DoubleArray(points.size)

            points.forEachIndexed { i, point ->
                x[i] = point.x
                y[i] = point.y
            }

            val approximations = definedApproximations

            val approximationsWithDeviance = mutableListOf<Pair<Approximation, Double>>()
            approximations.forEach { approximation ->
                try {
                    approximation.fit(x, y)
                    val deviance = approximation.std(x, y)
                    approximationsWithDeviance.add(approximation to deviance)
                } catch (e: Exception) {
                    println("[MainActivity]error: ${e.message}")
                }
            }

            ApproximationsStore.onAllApproximationsChange(approximationsWithDeviance)
        }
    }
}