package io.github.e1turin.entities.interpolations

import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.point.Point

abstract class Interpolation {
    abstract fun print(): String
    abstract fun fit(points: List<Point>)
    abstract val function: ((Double) -> Double)?
    abstract val color: Color
}