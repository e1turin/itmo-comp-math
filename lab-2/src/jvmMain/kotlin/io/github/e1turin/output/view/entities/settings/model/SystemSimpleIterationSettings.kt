package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.output.view.shared.lib.decompose.mutate


class SystemSimpleIterationSettings : SystemSettings {
    private val _data = MutableValue(
        SystemSimpleIterationData(
            range = -3.0..3.0,
            initialValue = 0.0
        )
    )
    override val data: Value<SystemSimpleIterationData> = _data

    val _isCompleted = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isCompleted

    override fun onSystemSelect(system: List<(List<Double>) -> Double>): Unit =
        _data.mutate { copy(system = system) }.also { _isCompleted.mutate { true } }

    fun onRangeChange(range: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = range) }

    fun onInitialValueChange(initialValue: Double): Unit =
        _data.mutate { copy(initialValue = initialValue) }

    data class SystemSimpleIterationData(
        val system: List<(List<Double>) -> Double>? = null,
        val range: ClosedRange<Double>,
        val initialValue: Double
    ) : Settings.Data()
}