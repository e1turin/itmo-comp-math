package io.github.e1turin.entities.interpolations

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object InterpolationsStore {
    private val _interpolations = mutableStateOf(emptyList<Interpolation>())
    public val interpolation: State<List<Interpolation>> = _interpolations

    fun onAllNewInterpolationsSet(interpolations: List<Interpolation>){
        _interpolations.value = interpolations
    }
}
