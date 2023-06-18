package io.github.e1turin.entities.finiteDifference

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.std.prettyVector

object FiniteDifferencesStore {
    private val _finiteDifferences = mutableStateOf(emptyList<List<Double>>())
    public val finiteDifferences: State<List<List<Double>>> = _finiteDifferences

    public fun calculateNewFiniteDifferences(points: List<Point>) {
        val points = points.sortedBy { it.x }

        val tmpFD = mutableListOf(points.map { it.y })
        for (newLayerSize in (points.size - 1) downTo 1) {
            val li = List<Double>(newLayerSize) { idx ->
                (tmpFD[tmpFD.size - 1][idx + 1] - tmpFD[tmpFD.size - 1][idx]) /
                        (points[idx + (points.size - newLayerSize)].x - points[idx].x)
            }
            tmpFD.add(li)
        }

        _finiteDifferences.value = tmpFD
    }

    public fun prettyPrintFD(): String {
        return _finiteDifferences.value.joinToString(
            prefix = "",
            separator = "\n",
            postfix = ""
        ) { it.prettyVector() }
    }
}