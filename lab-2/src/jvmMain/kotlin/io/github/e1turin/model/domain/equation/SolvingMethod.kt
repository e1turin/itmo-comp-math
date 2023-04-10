package io.github.e1turin.model.domain.equation

interface SolvingMethod<A> {
    fun nextApproximation(current: A): A
}

/*
sealed class ApproximationResult<X> {
    class Error<X>(val reason: Any): ApproximationResult<X>()
    class Range<X: Comparable<X>>(val range: ClosedRange<X>): ApproximationResult<X>()
    class Point<X>(val point: X): ApproximationResult<X>()
}
*/
