package io.github.e1turin.model.approximation

import io.github.e1turin.model.matrix.solveSLE
import io.github.e1turin.model.matrix.toMatrix
import kotlin.math.exp
import kotlin.math.ln

class PowerFunctionApproximation : Approximation {
    private var a0: Double? = null
    private var a1: Double? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! * exp(x * a1!!) }
        }

    override val params: List<Double>
        get() {
            checkState()
            return listOf(a0!!, a1!!)
        }

    /**
     * @throws ArithmeticException if regression can not be completed due to zero value of matrix determinant.
     */
    override fun fit(x: DoubleArray, y: DoubleArray) {
        require(x.size == y.size) { "x and y arrays must have equal amount of elements" }

        val x = x.map(::ln)
        val y = y.map(::ln)

        val n = x.size.toDouble()
        val sx = x.sum()
        val sy = y.sum()
        val sxx = x.reduce { acc, d -> acc + d * d }
        val sxy = x.reduceIndexed { idx, acc, d -> acc + d * y[idx]}

        val matrix = arrayOf(
            doubleArrayOf(n, sx),
            doubleArrayOf(sx, sxx),
        ).toMatrix()

        val vector = doubleArrayOf(sy, sxy)

        val solution = matrix.solveSLE(vector) ?: throw ArithmeticException("Can not solve system of equations")
        a0 = exp(solution[0])
        a1 = solution[1]
    }

    override fun predict(x: DoubleArray): DoubleArray {
        return DoubleArray(x.size) { function(x[it]) }
    }

    private fun checkState() = check(a0 != null && a1 != null) { "Model must be fitted before" }
}