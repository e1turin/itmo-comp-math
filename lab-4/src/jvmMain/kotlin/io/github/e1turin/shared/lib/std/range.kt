package io.github.e1turin.shared.lib.std

val ClosedRange<Double>.length: Double get() = endInclusive - start
val ClosedRange<Float>.length: Float get() = endInclusive - start
