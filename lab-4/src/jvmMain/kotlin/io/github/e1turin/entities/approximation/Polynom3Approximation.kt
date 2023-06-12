package io.github.e1turin.entities.approximation

import androidx.compose.ui.graphics.Color
import io.github.e1turin.entities.matrix.solveSLE
import io.github.e1turin.entities.matrix.toMatrix
import io.github.e1turin.shared.lib.compose.Random
import io.github.e1turin.shared.lib.std.pretty
import io.github.e1turin.shared.lib.std.prettyVector
import kotlin.math.pow

open class Polynom3Approximation : Approximation() {
    private var a0: Double? = null
    private var a1: Double? = null
    private var a2: Double? = null
    private var a3: Double? = null

    private var dataX: DoubleArray? = null
    private var dataY: DoubleArray? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! + a1!! * x + a2!! * x.pow(2) + a3!! * x.pow(3) }
        }

    override fun textView(): String {
        return "${a0?.pretty()} + ${a1?.pretty()} * x + ${a2?.pretty()} * x^2 + ${a3?.pretty()} * x^3"
    }

    override fun print(): String {
        return """
        Polynomial 3rd degree approximation (${textView()})
        X    =  ${dataX?.prettyVector()}
        Y    =  ${dataY?.prettyVector()}
        f(X) =  ${prediction?.prettyVector()}
        eps  =  ${deviance?.pretty()}    
        """.trimIndent()
    }

    override val color: Color = Color.Random

    override val params: List<Double>
        get() {
            checkState()
            return listOf(a0!!, a1!!, a2!!, a3!!)
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
        val sx3y = x.reduceIndexed { idx, acc, d -> acc + d.pow(3) * y[idx] }
        val sx5 = x.reduce { acc, d -> acc + d.pow(5) }
        val sx6 = x.reduce { acc, d -> acc + d.pow(6) }

        val matrix = arrayOf(
            doubleArrayOf(n, sx, sx2, sx3),
            doubleArrayOf(sx, sx2, sx3, sx4),
            doubleArrayOf(sx2, sx3, sx4, sx5),
            doubleArrayOf(sx3, sx4, sx5, sx6)
        ).toMatrix()

        val vector = doubleArrayOf(sy, sxy, sx2y, sx3y)

        val solution = matrix.solveSLE(vector) ?: throw ArithmeticException("Can not solve system of equations")
        a0 = solution[0]
        a1 = solution[1]
        a2 = solution[2]
        a3 = solution[3]

        dataX = x
        dataY = y
    }

    private fun checkState() =
        check(a0 != null && a1 != null && a2 != null && a3 != null) { "Model must be fitted before" }
}