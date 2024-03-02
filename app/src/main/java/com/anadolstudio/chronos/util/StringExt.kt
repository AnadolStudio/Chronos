package com.anadolstudio.chronos.util

import android.content.Context
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.util.data_time.Time

fun Time.toTrackTime(context: Context): String = when {
    hours > 0 && minutes > 0 -> context.getString(R.string.global_track_time_format, hours.toString(), minutes.toString())
    hours > 0 -> context.getString(R.string.global_track_time_hours_format, hours.toString())
    else -> minutes.toString()
}

fun Float.shortFormat(): String = String.format("%.1f", this).replace("[.,]0".toRegex(), "")

private const val ELLIPSIS = "…"

fun String.ellipsize(maxLength: Int): String = if (length > maxLength) {
    take(maxLength - 1).trim().plus(ELLIPSIS)
} else {
    this
}
