package io.github.e1turin.entities.interpolations

import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.point.Point

class LagrangeInterpolation : Interpolation() {
    override fun print(): String {
        return "Lagrange's interpolation polynomial"
    }

    override fun fit(points: List<Point>) {
        function = lambda@{ x: Double ->
            var res = 0.0
            for (i in points.indices) {
                var middleRes: Double = 1.0
                for (j in points.indices) {
                    if (i == j) continue
                    middleRes *= (x - points[j].x) / (points[i].x - points[j].x)

                }
                res += points[i].y * middleRes
            }
            return@lambda res
        }
    }

    override var function: ((Double) -> Double)? = null
        private set
    override val color: Color = Color.Green
}