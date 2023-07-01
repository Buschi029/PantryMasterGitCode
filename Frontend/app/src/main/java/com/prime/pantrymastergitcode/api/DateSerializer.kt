package com.prime.pantrymastergitcode.api

import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer {
    private val formatter = DateTimeFormatter.RFC_1123_DATE_TIME

    override fun deserialize(decoder: Decoder): LocalDate {
        val dateString = decoder.decodeString()
        return LocalDate.parse(dateString, DateSerializer.formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val dateString = formatter.format(value)
        encoder.encodeString(dateString)
    }
}

@Serializer(forClass = LocalDate::class)
object DateSerializer : KSerializer<LocalDate> {
     val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val formattedString = value.format(formatter)
        encoder.encodeString(formattedString)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val dateString = decoder.decodeString()
        return LocalDate.parse(dateString, formatter)
    }
}
