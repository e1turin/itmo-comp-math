package io.github.e1turin.output.view.entities.settings.model

sealed interface SystemSettings: Settings {
    fun onSystemSelect(system: List<(List<Double>)->Double>)
}