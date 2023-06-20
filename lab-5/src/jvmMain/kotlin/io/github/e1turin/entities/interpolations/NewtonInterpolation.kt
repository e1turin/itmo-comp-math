package io.github.e1turin.entities.interpolations

import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.finiteDifference.FiniteDifferencesStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.shared.lib.compose.Random
import kotlin.math.abs

class NewtonInterpolation : Interpolation() {
    private var name: String = "Newton's interpolation polynomial"

    override fun print(): String {
        return name
    }

    override fun fit(points: List<Point>) {
        val finiteDifferences by FiniteDifferencesStore.finiteDifferences
        require(finiteDifferences.size == points.size) { "Points and array of finite differences must have equal dimensions" }

        val sortedPoints = points.sortedBy { it.x }
        val xs = sortedPoints.map { it.x }
        require(checkCondition(xs)) { "Points must be distributed with equal distance" }

        val minX = points.minBy { it.x }.x
        val maxX = points.maxBy { it.x }.x
        val midX = (minX + maxX) / 2

        val difference = xs[1] - xs[0]


        /*
            function = lambda@{ x ->
                var res = finiteDifferences[0][0]
                for (k in 1..(points.size - 1)) {
                    var middleRes = finiteDifferences[k][0]
                    for (j in 0..(k - 1)) {
                        middleRes *= x - points[j].x
                    }
                    res += middleRes
                }
                return@lambda res
            }
        */

        val inspectingParam by PointsStore.inspectingParam
        function = lambda@{ x ->
            if (inspectingParam < midX) {
                name = "Newton's left interpolation polynomial"
                color = Color.Red
                val t = (x - xs[0]) / difference

                var prod = 1.0
                var sum = finiteDifferences[0][0]

                for (i in 1 until finiteDifferences.size) {
                    prod *= (t - i + 1) / i

                    sum += prod * finiteDifferences[i][0]
                }

                return@lambda sum
            } else {
                name = "Newton's right interpolation polynomial"
                color = Color.Blue

                val t = (x - xs[xs.size - 1]) / difference

                var prod = 1.0
                var sum = finiteDifferences[0][finiteDifferences.size - 1]

                for (i in 1 until finiteDifferences.size) {
                    prod *= (t + i - 1) / i

                    sum += prod * finiteDifferences[i][finiteDifferences.size - 1 - i]
                }

                return@lambda sum
            }
        }
    }

    private fun checkCondition(xs: List<Double>): Boolean {
        require(xs.size > 1) { "Method needs at least 2 points for interpolation" }


        val difference = xs[1] - xs[0]
        val eps = 1e-6

        if (abs(difference) < eps) return false

        for (i in 1..(xs.size - 2)) {
            if (abs(xs[i + 1] - xs[i] - difference) > eps)
                return false
        }
        return true
    }

    override var function: ((Double) -> Double)? = null
        private set
    override var color: Color = Color.Random
        private set
}