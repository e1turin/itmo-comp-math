package io.github.e1turin.output.view.shared.config

import io.github.e1turin.output.view.shared.lib.std.length
import kotlin.math.abs

const val msToMessageDisappear = 10_000L

const val maxBound = 10_000
fun Double.isReasonable(): Boolean {
    return abs(this) < maxBound
}

const val minRangeLength = 0.01
fun ClosedRange<Double>.isReasonable(): Boolean {
    return length > minRangeLength && endInclusive.isReasonable() && start.isReasonable()
}