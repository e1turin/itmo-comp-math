package io.github.e1turin.output.view.entities.settings.model

sealed interface EquationSettings : Settings {
    fun onEquationSelect(function: (Double) -> Double)
}