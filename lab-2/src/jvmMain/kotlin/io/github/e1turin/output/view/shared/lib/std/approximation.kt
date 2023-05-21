package io.github.e1turin.output.view.shared.lib.std

import kotlin.math.abs

const val threshold = 0.01
infix fun Double.approx(target: Double) =
    abs(this - target) < threshold
