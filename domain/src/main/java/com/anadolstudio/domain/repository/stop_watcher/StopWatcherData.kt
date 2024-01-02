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
    val inProgress: Boolean = startTime != null && endTime == null

    @IgnoredOnParcel
    val deltaTime: Time = Time((endTime ?: 0L) - (startTime ?: 0))

    @IgnoredOnParcel
    val state: State = when {
        startTime != null && endTime != null -> State.RESULT
        inProgress -> State.IN_PROGRESS
        else -> State.DEFAULT
    }

    enum class State {
        DEFAULT,
        IN_PROGRESS,
        RESULT
    }

}
