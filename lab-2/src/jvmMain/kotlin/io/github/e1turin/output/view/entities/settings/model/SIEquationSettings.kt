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
class SIEquationSettings : EquationSettings {
    private val _data = MutableValue(
        SIEquationData(
            range = calculateBoundsOfRange(0.0),
            initialValue = 0.0
        )
    )
    override val data: Value<SIEquationData> = _data

    private val _isComplete = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isComplete

    override fun onEquationSelect(function: (Double) -> Double) {
        _data.mutate { copy(function = function) }.also { _isComplete.mutate { true } }
    }

    override fun onApproximationFunctionSelect(function: (Double) -> Double) {
        _data.mutate { copy(approximationFunction = function) }
    }

    override fun onDerivativeFunctionSelect(function: (Double) -> Double) {
        _data.mutate { copy(derivativeFunction = function) }
    }

    fun onRangeChange(range: ClosedRange<Double>) {
        _data.mutate { copy(range = range) }
    }

    fun onInitialValueChange(initialValue: Double) {
        _data.mutate { copy(initialValue = initialValue) }
    }

    @Serializable
    @SerialName("SimpleIteration")
    data class SIEquationData(
        @Serializable(with = CFPRSerializer::class) val range: ClosedRange<Double>,
        val initialValue: Double,
        @Transient val function: ((Double) -> Double)? = null,
        @Transient val approximationFunction: ((Double) -> Double)? = null,
        @Transient val derivativeFunction: ((Double) -> Double)? = null
    ) : Settings.Data()
}