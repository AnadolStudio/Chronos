package com.anadolstudio.data.repository.chronos

import androidx.room.TypeConverter
import org.joda.time.DateTime
import java.util.UUID

object DatabaseConverters {

    @TypeConverter
    fun toDateTime(input: Long): DateTime = DateTime(input)

    @TypeConverter
    fun fromDateTime(input: DateTime): Long = input.withTimeAtStartOfDay().millis

    @TypeConverter
    fun toUUID(input: String): UUID = UUID.fromString(input)

    @TypeConverter
    fun fromUUID(input: UUID): String = input.toString()

}
