package io.github.e1turin.entities.approximation

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt

interface Approximation {
    val function: (Double) -> Double
    val params: List<Double>

    fun fit(x: DoubleArray, y: DoubleArray)

    fun predict(x: DoubleArray): DoubleArray {
        val f = function
        return DoubleArray(x.size) { f(x[it]) }
    }

    fun std(x: DoubleArray, y: DoubleArray): Double {
        require(x.size == y.size) { "vectors of parameters must have equal size" }
        val prediction = predict(x)
        var acc = 0.0
        var counter = 0
        for (i in prediction.indices) {
            acc += (prediction[i] - y[i]).let {
                if (it.isFinite()) {
                    counter++
                    it.pow(2)
                } else 0.0
            }
        }

        return sqrt(acc / counter)
    }

    fun textView(): String
    val color: Color
}