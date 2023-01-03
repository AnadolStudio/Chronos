package com.anadolstudio.domain

import androidx.room.TypeConverter
import java.util.UUID

object DatabaseConverters {

    @TypeConverter
    fun toUUID(input: String): UUID = UUID.fromString(input)

    @TypeConverter
    fun fromUUID(input: UUID): String = input.toString()

}
