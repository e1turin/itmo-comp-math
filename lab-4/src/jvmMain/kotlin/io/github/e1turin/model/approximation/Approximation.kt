package io.github.e1turin.model.approximation

interface Approximation {
    val function: (Double) -> Double
    val params: List<Double>

    fun fit(x: DoubleArray, y: DoubleArray)

    fun predict(x: DoubleArray): DoubleArray {
        return DoubleArray(x.size) { function(x[it]) }
    }
}