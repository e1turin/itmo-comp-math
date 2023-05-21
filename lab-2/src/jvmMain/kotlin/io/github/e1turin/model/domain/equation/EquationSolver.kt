package io.github.e1turin.model.domain.equation

interface EquationSolver<X, Y> {
    fun solve(f: (X) -> Y): X
}