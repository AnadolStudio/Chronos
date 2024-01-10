package com.anadolstudio.data.repository.common

import androidx.appcompat.app.AppCompatDelegate
import com.anadolstudio.core.util.common_extention.nullIfNotExist
import com.anadolstudio.core.util.preferences.modify
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.ironz.binaryprefs.Preferences
import org.joda.time.DateTime

@Suppress("TooManyFunctions")
class PreferencesStorage(private val preferences: Preferences) {

    private companion object {
        const val STOP_WATCHER_DATA_START_KEY = "STOP_WATCHER_DATA_START_KEY"
        const val STOP_WATCHER_DATA_END_KEY = "STOP_WATCHER_DATA_END_KEY"
        const val LAST_SELECTED_DATE = "LAST_SELECTED_DATE"
        const val NIGHT_MODE = "NIGHT_MODE"
    }

    var stopWatcherData: StopWatcherData
        set(value) = preferences.modify {
            putLong(STOP_WATCHER_DATA_START_KEY, value.startTime ?: -1)
            putLong(STOP_WATCHER_DATA_END_KEY, value.endTime ?: -1)
        }
        get() = StopWatcherData(
                startTime = preferences.getLong(STOP_WATCHER_DATA_START_KEY, -1).nullIfNotExist(),
                endTime = preferences.getLong(STOP_WATCHER_DATA_END_KEY, -1).nullIfNotExist(),
        )

    var lastSelectedDate: DateTime
        set(value) = preferences.modify {
            putLong(LAST_SELECTED_DATE, value.withTimeAtStartOfDay().millis)
        }
        get() = preferences.getLong(LAST_SELECTED_DATE, -1).nullIfNotExist()
                ?.let { DateTime(it) }
                ?: DateTime.now().withTimeAtStartOfDay()

    var nightMode: Int
        set(value) = preferences.modify {putInt(NIGHT_MODE, value)}
        get() = preferences.getInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}
