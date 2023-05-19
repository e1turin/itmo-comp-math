package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.model.util.CFPRSerializer
import io.github.e1turin.output.view.shared.lib.decompose.mutate
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*


class NewtonEquationSettings : EquationSettings {
    private val _data = MutableValue(
        NewtonData(
            range = -3.0..3.0,
            initialValue = 0.0
        )
    )
    override val data: Value<NewtonData> = _data

    private val _isComplete = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isComplete

    override fun onEquationSelect(function: (Double) -> Double): Unit =
        _data.mutate { copy(function = function) }.also { _isComplete.mutate { true } }

    fun onRangeChange(range: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = range) }

    fun onInitialValueChange(initialValue: Double): Unit =
        _data.mutate { copy(initialValue = initialValue) }

    @Serializable
    @SerialName("Newton")
    data class NewtonData(
        @Transient val function: ((Double) -> Double)? = null,
        @Serializable(with = CFPRSerializer::class) val range: ClosedRange<Double>,
        val initialValue: Double,
    ) : Settings.Data()
}

