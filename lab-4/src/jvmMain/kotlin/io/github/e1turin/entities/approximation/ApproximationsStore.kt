package io.github.e1turin.entities.approximation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ApproximationsStore {
    private val _approximations = mutableStateOf(emptyList<Approximation>())
    val approximations: State<List<Approximation>> = _approximations

    fun onApproximationAppend(approximation: Approximation) {
        _approximations.value = _approximations.value + approximation
    }

    fun onApproximationsClear() {
        _approximations.value = emptyList()
    }
}