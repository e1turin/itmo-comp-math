package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.model.util.CFPRSerializer
import io.github.e1turin.output.view.shared.lib.decompose.mutate
import io.github.e1turin.output.view.shared.lib.std.calculateBoundsOfRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * Settings of simple iteration method for numerical solving equations
 */
class SISystemSettings : SystemSettings {
    private val _data = MutableValue(
        SISystemData(
            range = listOf(
                calculateBoundsOfRange(0.0), // x
                calculateBoundsOfRange(0.0) // y
            ),
            initialValue = listOf(
                0.0, // x
                0.0 // y
            )
        )
    )
    override val data: Value<SISystemData> = _data

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


    fun onRangeChange(range: List<ClosedRange<Double>>) {
        _data.mutate { copy(range = range) }
    }

    fun onXRangeChange(xRange: ClosedRange<Double>) {
        _data.mutate { copy(range = listOf(xRange, range[1])) }
    }

    fun onYRangeChange(yRange: ClosedRange<Double>) {
        _data.mutate { copy(range = listOf(range[0], yRange)) }
    }

    fun onInitialValueChange(initialValue: List<Double>) {
        _data.mutate { copy(initialValue = initialValue) }
    }

    fun onInitialXValueChange(initialXValue: Double) {
        _data.mutate { copy(initialValue = listOf(initialXValue, initialValue[1])) }
    }

    fun onInitialYValueChange(initialYValue: Double) {
        _data.mutate { copy(initialValue = listOf(initialValue[0], initialYValue)) }
    }


    @Serializable
    @SerialName("SimpleIterationSystem")
    data class SISystemData(
        @Transient val system: List<(List<Double>) -> Double>? = null,
        val range: List<@Serializable(with = CFPRSerializer::class) ClosedRange<Double>>,
        val initialValue: List<Double>,
        @Transient val jacobian: List<List<(List<Double>) -> Double>>? = null,
        @Transient val approximationFunctions: List<(List<Double>) -> Double>? = null,
    ) : Settings.Data()
}