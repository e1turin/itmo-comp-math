package io.github.e1turin.model.util

val ((Double) -> Double).derivative: (Double) -> Double
    get() = { x ->
        val eps = 1e-8
        (this(x + eps) - this(x)) / eps
    }

operator fun <X, Y> List<(List<X>) -> Y>.invoke(args: List<X>): List<Y> {
    return List(size) {
        this[it].invoke(args)
    }
}