package io.github.e1turin.output.view.shared.lib.compose

fun ClosedRange<Double>.toFloatRange(): ClosedFloatingPointRange<Float> =
    start.toFloat()..endInclusive.toFloat()

fun ClosedRange<Float>.toDoubleRange(): ClosedRange<Double> =
    start.toDouble()..endInclusive.toDouble()
