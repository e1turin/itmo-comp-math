package io.github.e1turin.entities.approximation

interface Approximation {
    val function: (Double) -> Double
    val params: List<Double>

    fun fit(x: DoubleArray, y: DoubleArray)

    fun predict(x: DoubleArray): DoubleArray {
        val f = function
        return DoubleArray(x.size) { f(x[it]) }
    }

    fun textView(): String
}