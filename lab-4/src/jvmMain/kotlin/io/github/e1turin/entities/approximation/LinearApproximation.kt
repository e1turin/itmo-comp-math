package io.github.e1turin.entities.approximation

import io.github.e1turin.entities.matrix.solveSLE
import io.github.e1turin.entities.matrix.toMatrix

open class LinearApproximation : Approximation {
    protected open var a0: Double? = null
    protected open var a1: Double? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! + a1!! * x }
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
        val solution = fitLinear(x, y)
        a0 = solution[0]
        a1 = solution[1]
    }

    protected fun fitLinear(x: DoubleArray, y: DoubleArray): DoubleArray {
        require(x.size == y.size) { "x and y arrays must have equal amount of elements" }

        val n = x.size.toDouble()
        val sx = x.sum()
        val sy = y.sum()
        val sxx = x.reduce { acc, d -> acc + d * d }
        val sxy = x.reduceIndexed { idx, acc, d -> acc + d * y[idx] }

        val matrix = arrayOf(
            doubleArrayOf(n, sx),
            doubleArrayOf(sx, sxx),
        ).toMatrix()

        val vector = doubleArrayOf(sy, sxy)

        return matrix.solveSLE(vector) ?: throw ArithmeticException("Can not solve system of equations")
    }

    private fun checkState() = check(a0 != null && a1 != null) { "Model must be fitted before" }
}