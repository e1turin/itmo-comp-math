package io.github.e1turin.output.view.entities.form.model

data class Settings(
    val functionRangeStart: Double = -1.3,
    val functionRangeEnd: Double = 3.5,
    val graphGridStep: Double = 1.0,
    val graphDensity: Double = 0.025
)
