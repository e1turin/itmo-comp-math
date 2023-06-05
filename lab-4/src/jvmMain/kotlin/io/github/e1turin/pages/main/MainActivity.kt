package io.github.e1turin.pages.main

import androidx.compose.runtime.getValue
import io.github.e1turin.entities.approximation.*
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.entities.reposotory.JsonPointsRepository
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
                PointStore.onPointsAppend(points)
            } catch (e: Exception) {
                println("[MainActivity.kt]error: ${e.message}")
            }
        }
    }

    fun calculateApproximations() {
        scope.launch(Dispatchers.Default) {
            val points by PointStore.points
            val x = DoubleArray(points.size)
            val y = DoubleArray(points.size)

            points.forEachIndexed { i, point ->
                x[i] = point.x
                y[i] = point.y
            }

            val approximations = listOf(
                LinearApproximation(),
                Polynom2Approximation(),
                Polynom3Approximation(),
                ExponentialApproximation(),
                LogarithmApproximation(),
                PowerFunctionApproximation()
            )

            approximations.forEach { approximation ->
                try {
                    approximation.fit(x, y)
                    ApproximationsStore.onApproximationAppend(approximation)
                } catch (e: Exception) {
                    println("[MainActivity]error: ${e.message}")
                }
            }
        }
    }
}