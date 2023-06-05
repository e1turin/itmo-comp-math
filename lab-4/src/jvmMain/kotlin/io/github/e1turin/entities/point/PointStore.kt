package io.github.e1turin.entities.point

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object PointStore {
    private val _points = mutableStateOf(emptyList<Point>())
    val points: State<List<Point>> = _points

    fun onPointsAppend(points: List<Point>) {
        _points.value = _points.value.plus(points)
    }

    fun onPointsClean() {
        _points.value = emptyList()
    }
}