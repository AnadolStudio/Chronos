package com.anadolstudio.data.repository.chronos

import androidx.room.TypeConverter
import com.anadolstudio.core.util.data_time.safeParseDateTime
import org.joda.time.DateTime
import java.util.UUID

object DatabaseConverters {

    @TypeConverter
    fun toDateTime(input: String): DateTime = input.safeParseDateTime() ?: DateTime.now()

    @TypeConverter
    fun fromDateTime(input: DateTime): String = input.withTimeAtStartOfDay().toString()

    @TypeConverter
    fun toUUID(input: String): UUID = UUID.fromString(input)

    @TypeConverter
    fun fromUUID(input: UUID): String = input.toString()

}
