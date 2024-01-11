package com.anadolstudio.domain.repository.stop_watcher

import android.os.Parcelable
import com.anadolstudio.core.util.data_time.Time
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StopWatcherData(
        val startTime: Long? = null,
        val endTime: Long? = null,
) : Parcelable {

    @IgnoredOnParcel
    val deltaTime: Time?
        get() = when {
            endTime != null && startTime != null -> Time(endTime - startTime)
            else -> null
        }

    @IgnoredOnParcel
    val state: State = when {
        startTime != null && endTime != null -> State.RESULT
        startTime != null && endTime == null -> State.IN_PROGRESS
        else -> State.DEFAULT
    }

    enum class State {
        DEFAULT,
        IN_PROGRESS,
        RESULT
    }

}
