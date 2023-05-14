package io.github.e1turin.output.view.shared.lib.std


fun ClosedRange<Double>.toFloatRange(): ClosedFloatingPointRange<Float> =
    start.toFloat()..endInclusive.toFloat()

fun ClosedRange<Float>.toDoubleRange(): ClosedRange<Double> =
    start.toDouble()..endInclusive.toDouble()

fun List<ClosedRange<Double>>.toFloatRanges(): List<ClosedFloatingPointRange<Float>> =
    this.map { it.toFloatRange() }


/* Float */
val ClosedRange<Float>.length: Float get() = endInclusive - start

fun ClosedRange<Float>.slideToLowestBy(step: Float): ClosedFloatingPointRange<Float> =
    (start - step)..(endInclusive - step)

fun ClosedRange<Float>.slideToHighestBy(step: Float): ClosedFloatingPointRange<Float> =
    (start + step)..(endInclusive + step)

fun ClosedRange<Float>.shrinkBy(step: Float): ClosedFloatingPointRange<Float> =
    (start + step)..(endInclusive - step)

fun ClosedRange<Float>.stretchBy(step: Float): ClosedFloatingPointRange<Float> =
    (start - step)..(endInclusive + step)

inline infix fun ClosedRange<Float>.unsteadyStep(step: Float) = object : Iterator<Float> {
    private var current = start

    override fun hasNext(): Boolean =
        current + step <= endInclusive

    override fun next(): Float =
        current.also { current += step }
}


/* Double */
val ClosedRange<Double>.length: Double get() = endInclusive - start

fun ClosedRange<Double>.slideToLowestBy(step: Double): ClosedFloatingPointRange<Double> =
    (start - step)..(endInclusive - step)

fun ClosedRange<Double>.slideToHighestBy(step: Double): ClosedFloatingPointRange<Double> =
    (start + step)..(endInclusive + step)

fun ClosedRange<Double>.shrinkBy(step: Double): ClosedFloatingPointRange<Double> =
    (start + step)..(endInclusive - step)

fun ClosedRange<Double>.stretchBy(step: Double): ClosedFloatingPointRange<Double> =
    (start - step)..(endInclusive + step)

inline infix fun ClosedRange<Double>.unsteadyStep(step: Double) = object : Iterator<Double> {
    private var current = start

    override fun hasNext(): Boolean =
        current + step <= endInclusive

    override fun next(): Double =
        current.also { current += step }
}
