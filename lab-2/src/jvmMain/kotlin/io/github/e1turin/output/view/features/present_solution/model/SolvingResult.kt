package io.github.e1turin.output.view.features.present_solution.model

import java.lang.Exception

sealed interface SolvingResult {
    data class Error(val exception: Exception) : SolvingResult
    data class Number(val realNumber: Double) : SolvingResult
    data class Range(val range: ClosedRange<Double>) : SolvingResult
}