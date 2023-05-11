package io.github.e1turin.output.view.features.present_solution.model

import io.github.e1turin.output.view.shared.lib.std.pretty
import java.lang.Exception

sealed interface SolvingResult {
    data class Error(val exception: Exception) : SolvingResult
    data class Number(val realNumber: Double) : SolvingResult
    data class Range(val range: ClosedRange<Double>) : SolvingResult
}

fun SolvingResult.toResultString(): String = when (this) {
    is SolvingResult.Error -> "⚠ " + (exception.message ?: "Error appeared while computing solution")
    is SolvingResult.Number -> "x = " + realNumber.pretty()
    is SolvingResult.Range -> "range: " + range.pretty()
}
