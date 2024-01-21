package com.anadolstudio.chronos.util

import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

fun DateTime.plusDay(): DateTime = plusDays(1)

fun DateTime.minusDay(): DateTime = minusDays(1)

fun getMinutesInPeriod(startDate: DateTime, endDate: DateTime, includeLastDay: Boolean = false): Long {
    val endMillis = when (includeLastDay) {
        true -> endDate.plusDay().withTimeAtStartOfDay().millis
        false -> endDate.withTimeAtStartOfDay().millis
    }
    val millis = endMillis - startDate.withTimeAtStartOfDay().millis

    return TimeUnit.MILLISECONDS.toMinutes(millis)
}

fun DateTime.toSimpleDateFormat(): String = this.toString("dd.MM.YYYY")

fun DateTime.toWeekDayDateFormat(): String = this.toString("EE, dd.MM.YYYY").replaceFirstChar { it.uppercase() }
