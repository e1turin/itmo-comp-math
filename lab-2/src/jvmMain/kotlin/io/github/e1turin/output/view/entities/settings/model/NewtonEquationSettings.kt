package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.output.view.shared.lib.decompose.mutate


class NewtonEquationSettings : EquationSettings {
    private val _data = MutableValue(
        NewtonData(
            range = -3.0..3.0,
            initialValue = 0.0
        )
    )
    override val data: Value<NewtonData> = _data

    override val isCompleted: Boolean
        get() = data.value.function != null

    override fun onEquationSelect(function: (Double) -> Double): Unit =
        _data.mutate { copy(function = function) }

    fun onRangeChange(range: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = range) }

    fun onInitialValueChange(range: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = range) }

    data class NewtonData(
        val function: ((Double) -> Double)? = null,
        val range: ClosedRange<Double>,
        val initialValue: Double
    ) : Settings.Data()
}