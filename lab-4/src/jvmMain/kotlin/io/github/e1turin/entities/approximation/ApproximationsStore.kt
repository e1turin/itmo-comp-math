package io.github.e1turin.entities.approximation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ApproximationsStore {
    private val _approximations = mutableStateOf(emptyList<Pair<Approximation, Double>>())
    val approximations: State<List<Pair<Approximation, Double>>> = _approximations

    fun onApproximationAppend(approximation: Approximation, deviance: Double) {
        _approximations.value = _approximations.value + (approximation to deviance)
    }

    fun onAllApproximationsChange(approximations: List<Pair<Approximation, Double>>) {
        _approximations.value = approximations
    }

    fun onApproximationsClear() {
        _approximations.value = emptyList()
    }
}