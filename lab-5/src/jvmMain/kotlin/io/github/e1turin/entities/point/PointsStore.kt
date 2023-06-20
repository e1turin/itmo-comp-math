package io.github.e1turin.entities.point

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object PointsStore {
    private val _points = mutableStateOf(listOf<Point>())

    //        listOf<Point>(
//        Point(-1.0, -1.0),
//        Point(-2.0, -2.0),
//        Point(1.0, 1.0),
//        Point(0.0, -0.0)
//    ))
    val points: State<List<Point>> = _points

    private val _inspectingParam = mutableStateOf<Double>(0.0)
    public val inspectingParam: State<Double> = _inspectingParam

    fun onAllPointsAppend(points: List<Point>) {
        _points.value = points
    }

    fun onAllPointsEdit(changes: Map<Int, Point>) {
        _points.value = buildList {
            addAll(_points.value)
            changes.forEach {
                this[it.key] = it.value
            }
        }
    }

    fun onPointEdit(id: Int, point: Point) {
        _points.value = buildList {
            addAll(_points.value)
            this[id] = point
        }
    }

    fun onPointDelete(id: Int) {
        _points.value = buildList {
            addAll(_points.value.subList(0, id))
            addAll(_points.value.subList(id + 1, _points.value.size))
        }
    }

    fun onPointAppend(point: Point) {
        _points.value = buildList {
            addAll(_points.value)
            add(point)
        }
    }

    fun onPointsClean() {
        _points.value = emptyList<Point>()
    }

    fun onInspectingParamChange(newValue: Double) {
        _inspectingParam.value = newValue
    }
}