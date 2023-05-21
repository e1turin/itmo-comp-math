package io.github.e1turin.model.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

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
