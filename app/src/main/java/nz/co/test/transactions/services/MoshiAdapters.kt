package nz.co.test.transactions.services

import com.squareup.moshi.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeAdapter : JsonAdapter<OffsetDateTime>() {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    override fun fromJson(reader: JsonReader): OffsetDateTime? {
        return reader.nextString()?.let { OffsetDateTime.parse(it, formatter) }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: OffsetDateTime?) {
        writer.value(value?.format(formatter))
    }
}

class LocalDateTimeAdapter : JsonAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return try {
            val dateString = reader.nextString()
            if (dateString.isBlank()) null else LocalDateTime.parse(dateString, formatter)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        writer.value(value?.format(formatter))
    }
}


class BigDecimalAdapter : JsonAdapter<BigDecimal>() {
    @FromJson
    override fun fromJson(reader: JsonReader): BigDecimal {
        return reader.nextString().toBigDecimal()
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: BigDecimal?) {
        writer.value(value?.toPlainString())
    }
}