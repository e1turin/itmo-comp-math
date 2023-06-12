package io.github.e1turin.entities.approximation

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Approximation {
    abstract val function: (Double) -> Double
    abstract val params: List<Double>
    abstract val color: Color
    protected var prediction: DoubleArray? = null
    protected var deviance: Double? = null

    abstract fun textView(): String
    abstract fun print(): String

    abstract fun fit(x: DoubleArray, y: DoubleArray)

    fun predict(x: DoubleArray): DoubleArray {
        val f = function
        return DoubleArray(x.size) { f(x[it]) }.also { prediction = it }
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

        return sqrt(acc / counter).also { deviance = it }
    }
}