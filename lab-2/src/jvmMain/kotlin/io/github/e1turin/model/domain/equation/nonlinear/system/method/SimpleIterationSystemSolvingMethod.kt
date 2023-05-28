package io.github.e1turin.model.domain.equation.nonlinear.system.method

import io.github.e1turin.model.domain.equation.SolvingMethod
import io.github.e1turin.model.util.invoke
import io.github.e1turin.model.util.step
import kotlin.math.abs
import kotlin.math.max

class SimpleIterationSystemSolvingMethod(
    private val approximationFunction: List<(List<Double>) -> Double>,
) : SolvingMethod<List<Double>> {

    init {
        require(approximationFunction.size == dim) {
            "Invalid approximation function dimension. By agreement, the dimension of the codomain of system must be equal to $dim."
        }
    }

    override fun nextApproximation(current: List<Double>): List<Double> {
        require(approximationFunction.size == current.size) {
            "Invalid current approximation dimension. By agreement, the dimension of the domains of equations must be equal to $dim."
        }

        return approximationFunction(current)
    }

    companion object {
        const val dim: Int = 2

        fun approximationFunctionFrom(
            range: List<ClosedRange<Double>>,
        ): (List<Double>) -> Double {
            TODO("Not yet implemented")
        }

        fun testConvergenceCondition(
            range: List<ClosedRange<Double>>,
            stepsToCheck: List<Double> = listOf(1e-2, 1e-2),
            jacobianMatrix: List<List<(List<Double>) -> Double>>
        ): Boolean {
            require(range.size == dim) {
                "Invalid domain ranges dimension. By agreement, the dimension of the domains of equations must be equal to $dim."
            }

            var maxDerivativeValueInRange = 0.0

            for (x1 in range[0] step stepsToCheck[0]) {
                for (x2 in range[1] step stepsToCheck[1]) {
                    var maxDerivativeValueInMatrix = 0.0

                    jacobianMatrix.map { derivative ->
                        maxDerivativeValueInMatrix =
                            max(maxDerivativeValueInMatrix, derivative(listOf(x1, x2)).reduce { acc, d ->
                                acc + abs(d)
                            })
                    }

                    maxDerivativeValueInRange = max(
                        maxDerivativeValueInRange, maxDerivativeValueInMatrix
                    )
                }
            }
            return maxDerivativeValueInRange < 1
        }
    }
}