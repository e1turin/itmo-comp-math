package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
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

    val _isComplete = MutableValue(false)
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

object CFPRSerializer : KSerializer<ClosedRange<Double>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("kotlin.ranges.DoubleClosedRange") {
        element<Double>("start")
        element<Double>("endInclusive")
    }

    override fun serialize(encoder: Encoder, value: ClosedRange<Double>) {
        encoder.encodeStructure(descriptor) {
            encodeDoubleElement(descriptor, 0, value.start)
            encodeDoubleElement(descriptor, 1, value.endInclusive)
        }
    }

    override fun deserialize(decoder: Decoder): ClosedRange<Double> =
        decoder.decodeStructure(descriptor) {
            var start: Double? = null
            var end: Double? = null
            while (true) {
                val index = decodeElementIndex(descriptor)
                if (index == CompositeDecoder.DECODE_DONE) break
                if (index == 0) start = decodeDoubleElement(descriptor, index)
                else end = decodeDoubleElement(descriptor, index)
            }
            if (start == null || end == null) throw SerializationException("Error appeared while deserialization")
            return@decodeStructure start..end
        }
}
