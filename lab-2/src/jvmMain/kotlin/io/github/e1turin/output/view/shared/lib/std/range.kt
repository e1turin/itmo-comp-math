package io.github.e1turin.output.view.shared.lib.std

val ClosedRange<Float>.length: Float get() = endInclusive - start
val ClosedRange<Double>.length: Double get() = endInclusive - start

fun ClosedRange<Double>.toFloatRange(): ClosedFloatingPointRange<Float> =
    start.toFloat()..endInclusive.toFloat()

fun ClosedRange<Float>.toDoubleRange(): ClosedRange<Double> =
    start.toDouble()..endInclusive.toDouble()

fun ClosedRange<Float>.slideToLowestBy(step: Float): ClosedFloatingPointRange<Float> =
    (start - step)..(endInclusive - step)

fun ClosedRange<Float>.slideToHighestBy(step: Float): ClosedFloatingPointRange<Float> =
    (start + step)..(endInclusive + step)

fun ClosedRange<Float>.shrinkBy(step: Float): ClosedFloatingPointRange<Float> =
    (start + step)..(endInclusive - step)

fun ClosedRange<Float>.stretchBy(step: Float): ClosedFloatingPointRange<Float> =
    (start - step)..(endInclusive + step)
