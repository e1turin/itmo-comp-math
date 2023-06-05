package io.github.e1turin.entities.approximation

import kotlin.math.exp
import kotlin.math.ln

open class ExponentialApproximation : LinearApproximation() {
    override var a0: Double? = null
    override var a1: Double? = null

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

        val lny = DoubleArray(y.size) { ln(y[it]) }

        val solution = fitLinear(x, lny)
        a0 = exp(solution[0])
        a1 = solution[1]
    }

    private fun checkState() = check(a0 != null && a1 != null) { "Model must be fitted before" }
}