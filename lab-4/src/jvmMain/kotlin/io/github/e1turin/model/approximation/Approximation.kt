package io.github.e1turin.model.approximation

interface Approximation {
    fun fit(x: DoubleArray, y: DoubleArray)
    fun predict(x: DoubleArray): DoubleArray
    val function: (Double) -> Double
    val params: List<Double>
}