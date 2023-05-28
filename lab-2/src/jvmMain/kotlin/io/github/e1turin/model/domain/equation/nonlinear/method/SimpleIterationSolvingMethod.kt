package io.github.e1turin.model.domain.equation.nonlinear.method

import io.github.e1turin.model.domain.equation.SolvingMethod
import io.github.e1turin.model.util.derivative
import io.github.e1turin.model.util.step
import kotlin.math.abs
import kotlin.math.max

class SimpleIterationSolvingMethod(
    private val approximationFunction: (Double) -> Double,
) : SolvingMethod<Double> {

    override fun nextApproximation(current: Double): Double {
        return approximationFunction(current)
    }

    companion object {
        fun approximationFunctionFrom(
            range: ClosedRange<Double>,
            stepToCheck: Double = 1e-2,
            function: (Double) -> Double,
            derivative: (Double) -> Double = function.derivative,
        ): Pair<(Double) -> Double, (Double) -> Double> {
            val lambda = -1 / maxAbsDerivativeValue(
                range, stepToCheck, derivative
            )

            return Pair({ x: Double -> x + lambda * function(x) },  // phi(x)
                { x: Double -> 1 + lambda * derivative(x) }         // d(phi(x))/dx
            )
        }

        fun testConvergenceCondition(
            range: ClosedRange<Double>, stepToCheck: Double = 1e-2, derivative: (Double) -> Double
        ): Boolean {
            return maxAbsDerivativeValue(range, stepToCheck, derivative) < 1
        }

        private fun maxAbsDerivativeValue(
            range: ClosedRange<Double>, stepToCheck: Double = 1e-2, derivative: (Double) -> Double
        ): Double {
            var maxAbsDerivativeValue = 1.0

            for (x in range step stepToCheck) {
                maxAbsDerivativeValue = max(maxAbsDerivativeValue, abs(derivative(x)))
            }

            return maxAbsDerivativeValue
        }
    }
}