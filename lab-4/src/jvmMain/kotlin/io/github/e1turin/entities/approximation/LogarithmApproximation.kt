package io.github.e1turin.entities.approximation

import androidx.compose.ui.graphics.Color
import io.github.e1turin.shared.lib.compose.Random
import io.github.e1turin.shared.lib.std.pretty
import io.github.e1turin.shared.lib.std.prettyVector
import kotlin.math.exp
import kotlin.math.ln

open class LogarithmApproximation : LinearApproximation() {
    var a0: Double? = null
    var a1: Double? = null

    private var dataX: DoubleArray? = null
    private var dataY: DoubleArray? = null

    override val function: (Double) -> Double
        get() {
            checkState()
            return { x -> a0!! * ln(x) + a1!! }
        }

    override fun textView(): String {
        return "${a0?.pretty()} * ln(x) + ${a1?.pretty()}"
    }

    override fun print(): String {
        return """
        Logarithmic approximation (${textView()})
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
            return listOf(a0!!, a1!!)
        }

    /**
     * Method avoids negative values for x.
     * @throws ArithmeticException if regression can not be completed due to zero value of matrix determinant.
     */
    override fun fit(x: DoubleArray, y: DoubleArray) {
        require(x.size == y.size) { "x and y arrays must have equal amount of elements" }

        val xy = x.zip(y).filter { it.first > 0 }

        val lnFilteredX = DoubleArray(xy.size) { ln(xy[it].first) }
        val filteredY = DoubleArray(xy.size) { xy[it].second }

        val solution = fitLinear(lnFilteredX, filteredY)
        a0 = exp(solution[0])
        a1 = solution[1]

        dataX = x
        dataY = y
    }

    private fun checkState() = check(a0 != null && a1 != null) { "Model must be fitted before" }
}