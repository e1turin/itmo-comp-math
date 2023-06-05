package io.github.e1turin.entities.approximation

import io.github.e1turin.entities.matrix.solveSLE
import io.github.e1turin.entities.matrix.toMatrix
import io.github.e1turin.shared.lib.std.pretty
import kotlin.math.pow

open class Polynom2Approximation : Approximation {
    private var a0: Double? = null
    private var a1: Double? = null
    private var a2: Double? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! + a1!! * x + a2!! * x.pow(2) }
        }

    override fun textView(): String {
        return "${a0?.pretty()} + ${a1?.pretty()} * x + ${a2?.pretty()} * x^2"
    }

    override val params: List<Double>
        get() {
            checkState()
            return listOf(a0!!, a1!!, a2!!)
        }

    /**
     * @throws ArithmeticException if regression can not be completed due to zero value of matrix determinant.
     */
    override fun fit(x: DoubleArray, y: DoubleArray) {
        require(x.size == y.size) { "x and y arrays must have equal amount of elements" }

        val n = x.size.toDouble()
        val sx = x.sum()
        val sy = y.sum()
        val sx2 = x.reduce { acc, d -> acc + d * d }
        val sxy = x.reduceIndexed { idx, acc, d -> acc + d * y[idx] }
        val sx3 = x.reduce { acc, d -> acc + d.pow(3) }
        val sx2y = x.reduceIndexed { idx, acc, d -> acc + d * d * y[idx] }
        val sx4 = x.reduce { acc, d -> acc + d.pow(4) }

        val matrix = arrayOf(
            doubleArrayOf(n, sx, sx2),
            doubleArrayOf(sx, sx2, sx3),
            doubleArrayOf(sx2, sx3, sx4)
        ).toMatrix()

        val vector = doubleArrayOf(sy, sxy, sx2y)

        val solution = matrix.solveSLE(vector) ?: throw ArithmeticException("Can not solve system of equations")
        a0 = solution[0]
        a1 = solution[1]
        a2 = solution[2]
    }

    private fun checkState() = check(a0 != null && a1 != null && a2 != null) { "Model must be fitted before" }
}