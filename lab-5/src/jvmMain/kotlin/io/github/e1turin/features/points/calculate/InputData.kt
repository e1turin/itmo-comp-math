package io.github.e1turin.features.points.calculate

data class InputData(
    val range: ClosedFloatingPointRange<Double>,
    val divisions: Int,
    val functionLabel: String
)