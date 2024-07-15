package com.anadolstudio.data.repository.chronos

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.UUID
import java.util.concurrent.TimeUnit

object DatabaseConverters {

    @TypeConverter
    fun toDateTime(days: Long): DateTime = DateTime(TimeUnit.DAYS.toMillis(days))

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        val offset = dateTime.zone.getOffset(dateTime.millis)
        val correctData = dateTime.plus(offset.toLong()).withZone(DateTimeZone.forID("GMT"))

        return TimeUnit.MILLISECONDS.toDays(correctData.withTimeAtStartOfDay().millis)
    }

    @TypeConverter
    fun toUUID(input: String): UUID = UUID.fromString(input)

    @TypeConverter
    fun fromUUID(input: UUID): String = input.toString()

}
