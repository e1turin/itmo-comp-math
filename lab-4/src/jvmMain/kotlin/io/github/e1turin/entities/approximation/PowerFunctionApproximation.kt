package io.github.e1turin.entities.approximation

import io.github.e1turin.shared.lib.std.pretty
import kotlin.math.*

open class PowerFunctionApproximation : LinearApproximation() {
    override var a0: Double? = null
    override var a1: Double? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! * x.pow(a1!!) }
        }

    override fun textView(): String {
        return "${a0?.pretty()} * x^(${a1?.pretty()})"
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

        val lnx = DoubleArray(x.size) { sign(x[it]) * ln(abs(x[it])) }
        val lny = DoubleArray(y.size) { sign(y[it]) * ln(abs(y[it])) }

        println(lny.joinToString(separator = ", ") { it.toString() })

        val solution = fitLinear(lnx, lny)
        a0 = exp(solution[0])
        a1 = solution[1]
    }

    private fun checkState() = check(a0 != null && a1 != null) { "Model must be fitted before" }
}