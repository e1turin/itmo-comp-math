package io.github.e1turin.model.domain.equation.nonlinear.solver

import io.github.e1turin.model.domain.equation.EquationSolver
import io.github.e1turin.model.domain.equation.SolvingMethod

class IterativeEquationSolver(
    private val method: SolvingMethod<Double>,
    private val initialApproximation: Double,
    private val stopCondition: (Double) -> Boolean,
) : EquationSolver<Double, Double> {

    override fun solve(f: (Double) -> Double): Double {
        var approximation = initialApproximation

        do {
            approximation = method.nextApproximation(approximation)
//            check(approximation.isFinite()) { "Non finite value while an iteration" }
        } while (!stopCondition(approximation))

        return approximation
    }

}