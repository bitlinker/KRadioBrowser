package io.github.bitlinker.kradiobrowser.internal.serializers

import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Simple serializer for the following date format:
 * YYYY-MM-DD HH:mm:ss
 */
internal class SimpleDateNullableSerializer : KSerializer<Instant?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant?) {
        encoder.encodeString(value?.format(format) ?: "")
    }

    override fun deserialize(decoder: Decoder): Instant? {
        val string = decoder.decodeString()
        if (string.isEmpty()) return null
        return Instant.parse(
            input = string,
            format = format,
        )
    }
}

internal class SimpleDateSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.format(format))
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(
            input = decoder.decodeString(),
            format = format,
        )
    }
}

private val format: DateTimeFormat<DateTimeComponents> = Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
}