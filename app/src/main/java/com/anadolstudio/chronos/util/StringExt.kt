package com.anadolstudio.chronos.util

import android.content.Context
import com.anadolstudio.chronos.R
import com.anadolstudio.core.util.data_time.Time

fun Time.toTrackTime(context: Context): String = when (hours > 0) {
    true -> context.getString(R.string.global_track_time_format, hours.toString(), minutes.toString())
    false -> minutes.toString()
}
