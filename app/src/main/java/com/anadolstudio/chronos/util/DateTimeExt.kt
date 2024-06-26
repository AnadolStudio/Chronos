package com.anadolstudio.chronos.util

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.SUNDAY
import java.util.concurrent.TimeUnit

fun DateTime.plusDay(): DateTime = plusDays(1)

fun DateTime.minusDay(): DateTime = minusDays(1)

fun DateTime.plusWeek(): DateTime = plusWeeks(1)

fun DateTime.minusWeek(): DateTime = minusWeeks(1)

fun DateTime.plusMonth(): DateTime = plusMonths(1)

fun DateTime.minusMonth(): DateTime = minusMonths(1)

fun getMinutesInPeriod(startDate: DateTime, endDate: DateTime, includeLastDay: Boolean = false): Long {
    val endMillis = when (includeLastDay) {
        true -> endDate.plusDay().withTimeAtStartOfDay().millis
        false -> endDate.withTimeAtStartOfDay().millis
    }
    val millis = endMillis - startDate.withTimeAtStartOfDay().millis

    return TimeUnit.MILLISECONDS.toMinutes(millis)
}

fun DateTime.toSimpleDateFormat(): String = this.toString("dd.MM.YYYY")

fun DateTime.toWeekDayDateFormat(): String = this.toString("EE, dd MMMM").replaceFirstChar { it.uppercase() }

val TODAY: DateTime get() = DateTime.now().withTimeAtStartOfDay()

val DateTime.startWeek: DateTime get() = this.minusDays(dayOfWeek - 1)

val DateTime.endWeek: DateTime get() = this.plusDays(SUNDAY - dayOfWeek)

val DateTime.startMonth: DateTime get() = this.minusDays(dayOfMonth - 1)

val DateTime.endMonth: DateTime get() {
    val nextMonth = this.plusMonth()

    return nextMonth.minusDays(nextMonth.dayOfMonth)
}

val DateTime.orToday: DateTime get() = if (isAfter(TODAY)) TODAY else this
