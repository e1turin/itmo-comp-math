package io.github.e1turin.shared.config

import kotlin.math.log

val functionWithLabelStore: Map<String, (Double) -> Double> = mapOf(
    "x" to { x -> x },
    "x^2" to { x -> x * x },
    "ln(x)" to { x -> log(x, kotlin.math.E) }
)