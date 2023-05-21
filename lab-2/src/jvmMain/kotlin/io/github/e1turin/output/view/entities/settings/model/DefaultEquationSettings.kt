package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

object DefaultEquationSettings : DefaultSettings, EquationSettings {

    override val data: Value<Data> = MutableValue(Data)
    override val isCompleted: Value<Boolean> = MutableValue(false)

    override fun onEquationSelect(function: (Double) -> Double) {}
    override fun onApproximationFunctionSelect(function: (Double) -> Double) {}
    override fun onDerivativeFunctionSelect(function: (Double) -> Double) {}

    object Data : Settings.Data()
}