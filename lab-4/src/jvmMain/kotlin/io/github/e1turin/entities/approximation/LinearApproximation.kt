package io.github.e1turin.entities.approximation

import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.matrix.solveSLE
import io.github.e1turin.entities.matrix.toMatrix
import io.github.e1turin.shared.lib.compose.Random
import io.github.e1turin.shared.lib.std.pretty
import io.github.e1turin.shared.lib.std.prettyVector
import kotlin.math.pow
import kotlin.math.sqrt

open class LinearApproximation : Approximation() {
    private var a0: Double? = null
    private var a1: Double? = null

    private var dataX: DoubleArray? = null
    private var dataY: DoubleArray? = null
    private var pirsonCoef: Double? = null

    override val color: Color = Color.Random

    override val params: List<Double>
        get() {
            checkState()
            return listOf(a0!!, a1!!)
        }

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! + a1!! * x }
        }

    override fun textView(): String {
        return "${a0?.pretty()} + ${a1?.pretty()} * x"
    }

    override fun print(): String {
        calculatePirsonCoef()

        return """
        Linear approximation (${textView()})
        X    =  ${dataX?.prettyVector()}
        Y    =  ${dataY?.prettyVector()}
        f(X) =  ${prediction?.prettyVector()}
        eps  =  ${deviance?.pretty()}    
        r    =  ${pirsonCoef?.pretty()}
        """.trimIndent()
    }

    private fun calculatePirsonCoef() {
        checkState()
        val meanX = dataX!!.sum() / dataX!!.size
        val meanY = dataY!!.sum() / dataY!!.size
        val x = dataX!!.asSequence().map { it - meanX }
        val y = dataY!!.asSequence().map { it - meanY }
        val t1 = x.zip(y).map { it.first * it.second }.sum()
        val t2 = x.map { it.pow(2) }.sum() * y.map { it.pow(2) }.sum()
        val res = t1 / sqrt(t2)
        pirsonCoef = res
    }

    /**
     * @throws ArithmeticException if regression can not be completed due to zero value of matrix determinant.
     */
    override fun fit(x: DoubleArray, y: DoubleArray) {
        val solution = fitLinear(x, y)
        a0 = solution[0]
        a1 = solution[1]
        dataX = x
        dataY = y
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