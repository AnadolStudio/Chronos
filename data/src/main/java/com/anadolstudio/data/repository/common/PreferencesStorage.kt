package com.anadolstudio.data.repository.common

import com.anadolstudio.core.util.common_extention.nullIfNotExist
import com.anadolstudio.core.util.preferences.modify
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.ironz.binaryprefs.Preferences

@Suppress("TooManyFunctions")
class PreferencesStorage(private val preferences: Preferences) {

    private companion object {
        const val STOP_WATCHER_DATA_START_KEY = "STOP_WATCHER_DATA_START_KEY"
        const val STOP_WATCHER_DATA_END_KEY = "STOP_WATCHER_DATA_END_KEY"
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

}
