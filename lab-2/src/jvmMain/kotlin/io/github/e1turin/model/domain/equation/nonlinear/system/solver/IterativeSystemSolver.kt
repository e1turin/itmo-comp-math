package io.github.e1turin.model.domain.equation.nonlinear.system.solver

import io.github.e1turin.model.domain.equation.SolvingMethod
import io.github.e1turin.model.domain.equation.nonlinear.system.SystemSolver

class IterativeSystemSolver(
    private val method: SolvingMethod<List<Double>>,
    private val initialApproximation: List<Double>,
    private val stopCondition: (List<Double>) -> Boolean,
) : SystemSolver<Double, Double> {

    override fun solve(system: List<(List<Double>) -> Double>): List<Double> {
        var approximation = initialApproximation

        do {
            approximation = method.nextApproximation(approximation)
        } while (!stopCondition(approximation))

        return approximation
    }

}