package io.github.e1turin.model.domain.equation.nonlinear.solver

import io.github.e1turin.model.domain.equation.EquationSolver
import io.github.e1turin.model.domain.equation.SolvingMethod

class TelescopicEquationSolver(
    private val method: SolvingMethod<ClosedRange<Double>>,
    private val initialRange: ClosedRange<Double>,
    private val stopCondition: (ClosedRange<Double>) -> Boolean,
) : EquationSolver<Double, Double> {

    override fun solve(f: (Double) -> Double): Double {
        var approximation = initialRange

        do {
            approximation = method.nextApproximation(approximation)
        } while (!stopCondition(approximation))

        return (approximation.endInclusive + approximation.start) / 2
    }

}