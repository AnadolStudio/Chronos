package com.anadolstudio.data.database

import androidx.room.TypeConverter
import com.anadolstudio.core.data_time.safeParseDateTime
import org.joda.time.DateTime
import java.util.UUID

object DatabaseConverters {

    @TypeConverter
    fun toDateTime(input: String): DateTime? = input.safeParseDateTime()

    @TypeConverter
    fun fromDateTime(input: DateTime): String = input.withTimeAtStartOfDay().toString()

    @TypeConverter
    fun toUUID(input: String): UUID = UUID.fromString(input)

    @TypeConverter
    fun fromUUID(input: UUID): String = input.toString()


}
