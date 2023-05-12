package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.output.view.shared.lib.decompose.mutate


class SystemSimpleIterationSettings : SystemSettings {
    private val _data = MutableValue(
        SystemSimpleIterationData(
            range = listOf(
                -3.0..3.0, // x
                -3.0..3.0 // y
            ),
            initialValue = listOf(
                0.0, // x
                0.0 // y
            )
        )
    )
    override val data: Value<SystemSimpleIterationData> = _data

    private val _isCompleted = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isCompleted

    override fun onSystemSelect(system: List<(List<Double>) -> Double>) {
        _data.mutate { copy(system = system) }.also { _isCompleted.mutate { true } }
    }

    override fun onJacobianSelect(jacobian: List<List<(List<Double>) -> Double>>) {
        _data.mutate { copy(jacobian = jacobian) }
    }

    override fun onApproximationFunctionsSelect(system: List<(List<Double>) -> Double>) {
        _data.mutate { copy(approximationFunctions = system) }
    }


    fun onRangeChange(range: List<ClosedRange<Double>>): Unit =
        _data.mutate { copy(range = range) }

    fun onXRangeChange(xRange: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = listOf(xRange, range[1])) }

    fun onYRangeChange(yRange: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = listOf(range[0], yRange)) }


    fun onInitialValueChange(initialValue: List<Double>): Unit =
        _data.mutate { copy(initialValue = initialValue) }

    fun onInitialXValueChange(initialXValue: Double): Unit =
        _data.mutate { copy(initialValue = listOf(initialXValue, initialValue[1])) }

    fun onInitialYValueChange(initialYValue: Double): Unit =
        _data.mutate { copy(initialValue = listOf(initialValue[0], initialYValue)) }


    data class SystemSimpleIterationData(
        val system: List<(List<Double>) -> Double>? = null,
        val range: List<ClosedRange<Double>>,
        val initialValue: List<Double>,
        val jacobian: List<List<(List<Double>) -> Double>>? = null,
        val approximationFunctions: List<(List<Double>) -> Double>? = null,
    ) : Settings.Data()
}