package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.model.util.CFPRSerializer
import io.github.e1turin.output.view.shared.lib.decompose.mutate
import io.github.e1turin.output.view.shared.lib.std.calculateBoundsOfRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


class ChordEquationSettings : EquationSettings {
    private val _data = MutableValue(
        ChordData(
            range = calculateBoundsOfRange(0.0),
        )
    )
    override val data: Value<ChordData> = _data

    private val _isComplete = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isComplete

    override fun onEquationSelect(function: (Double) -> Double): Unit =
        _data.mutate { copy(function = function) }.also { _isComplete.mutate { true } }

    override fun onApproximationFunctionSelect(function: (Double) -> Double) {
        /* Unnecessary operation */
    }

    override fun onDerivativeFunctionSelect(function: (Double) -> Double) {
        /* Unnecessary operation */
    }

    fun onRangeChange(range: ClosedRange<Double>) {
        _data.mutate { copy(range = range) }
    }

    @Serializable
    @SerialName("Chord")
    data class ChordData(
        @Transient val function: ((Double) -> Double)? = null,
        @Serializable(with = CFPRSerializer::class) val range: ClosedRange<Double>,
    ) : Settings.Data()
}

