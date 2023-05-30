package io.github.e1turin.shared.config

import kotlin.math.log
import kotlin.math.sin

val functionWithLabelStore: Map<String, (Double) -> Double> = mapOf(
    "x" to { x -> x },
    "x^2" to { x -> x * x },
    "ln(x)" to { x -> log(x, kotlin.math.E) },
    "1/x" to { x -> 1 / x },
    "sin(x)/x" to { x -> sin(x) / x }
)

const val maxNIteration = 20