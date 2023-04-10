package io.github.e1turin.model.util

val ((Double) -> Double).derivative: (Double) -> Double
    get() = { x ->
        val eps = 1e-8
        (this(x + eps) - this(x)) / eps
    }