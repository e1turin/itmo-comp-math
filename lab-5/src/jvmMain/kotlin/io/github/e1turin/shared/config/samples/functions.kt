package io.github.e1turin.shared.config.samples

import kotlin.math.sin

val availableFunctions = mapOf<String, (Double) -> Double>(
    "x" to { x -> x },
    "sin(x)" to { x -> sin(x) }
)