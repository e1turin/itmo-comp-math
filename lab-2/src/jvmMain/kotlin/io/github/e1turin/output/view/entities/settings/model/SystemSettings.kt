package io.github.e1turin.output.view.entities.settings.model

sealed interface SystemSettings : Settings {
    fun onSystemSelect(system: List<(List<Double>) -> Double>)
    fun onJacobianSelect(jacobian: List<List<(List<Double>) -> Double>>)
    fun onApproximationFunctionsSelect(system: List<(List<Double>) -> Double>)
}