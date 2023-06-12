package io.github.e1turin.shared.config

import io.github.e1turin.entities.approximation.*


val definedApproximations = listOf(
    LinearApproximation(),
    Polynom2Approximation(),
    Polynom3Approximation(),
    ExponentialApproximation(),
    LogarithmApproximation(),
    PowerFunctionApproximation()
)
