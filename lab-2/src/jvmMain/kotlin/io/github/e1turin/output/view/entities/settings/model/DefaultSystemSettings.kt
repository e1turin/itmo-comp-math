package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

object DefaultSystemSettings : DefaultSettings, SystemSettings {
    override val data: Value<Data> = MutableValue(Data())
    override val isCompleted: Value<Boolean> = MutableValue(false)

    override fun onSystemSelect(system: List<(List<Double>) -> Double>) {}
    override fun onJacobianSelect(jacobian: List<List<(List<Double>) -> Double>>) {}
    override fun onApproximationFunctionsSelect(system: List<(List<Double>) -> Double>) {}

    class Data : Settings.Data()
}