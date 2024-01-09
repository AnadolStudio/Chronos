package com.anadolstudio.chronos.util

import org.joda.time.DateTime

fun DateTime.plusDay(): DateTime = plusDays(1)

fun DateTime.minusDay(): DateTime = minusDays(1)

fun DateTime.toSimpleDateFormat(): String = this.toString("dd.MM.YYYY")
