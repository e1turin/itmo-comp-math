package io.github.e1turin.entities.interpolations

import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.finiteDifference.FiniteDifferencesStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.compose.Random

class NewtonInterpolation : Interpolation() {
    override fun print(): String {
        TODO("Not yet implemented")
    }

    override fun fit(points: List<Point>) {
        val finiteDifferences by FiniteDifferencesStore.finiteDifferences
        require(finiteDifferences.size == points.size)

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
    }

    override var function: ((Double) -> Double)? = null
        private set
    override val color: Color = Color.Random
}