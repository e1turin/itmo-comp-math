package io.github.e1turin.model.util

import kotlin.math.sqrt

internal fun vectorLength(vector: List<Double>) =
    sqrt(vector.reduce { acc, d -> acc + d*d })
