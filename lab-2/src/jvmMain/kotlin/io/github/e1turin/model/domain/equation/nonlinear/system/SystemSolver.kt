package io.github.e1turin.model.domain.equation.nonlinear.system

interface SystemSolver<X, Y> {
    fun solve(system: List<(List<X>) -> Y>): List<X>
}
