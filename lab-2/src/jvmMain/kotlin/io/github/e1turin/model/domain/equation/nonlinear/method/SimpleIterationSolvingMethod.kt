package io.github.e1turin.model.domain.equation.nonlinear.method

import io.github.e1turin.model.domain.equation.SolvingMethod
import io.github.e1turin.model.util.derivative
import io.github.e1turin.model.util.step
import kotlin.math.abs
import kotlin.math.max

class SimpleIterationSolvingMethod(
    private val devianceFunction: (Double) -> Double,
) : SolvingMethod<Double> {

    override fun nextApproximation(current: Double): Double {
        return devianceFunction(current)
    }

    companion object {
        fun devianceFunctionFrom(
            range: ClosedRange<Double>,
            function: (Double) -> Double,
            derivative: (Double) -> Double = function.derivative,
            stepToCheck: Double = 1e-2
        ): Pair<(Double) -> Double, (Double) -> Double> {
            var maxAbsDerivativeValue = 1.0

            for (x in range step stepToCheck) {
                maxAbsDerivativeValue = max(maxAbsDerivativeValue, abs(derivative(x)))
            }

            val lambda = -1 / maxAbsDerivativeValue

            return Pair(
                { x: Double -> x + lambda * function(x) },
                { x: Double -> 1 + lambda * derivative(x) }
            )
        }
    }

}