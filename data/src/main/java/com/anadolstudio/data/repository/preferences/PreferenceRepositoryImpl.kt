package com.anadolstudio.data.repository.preferences

import com.anadolstudio.domain.repository.stop_watcher.PreferenceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData

class PreferenceRepositoryImpl(private val preferencesStorage: PreferencesStorage) : PreferenceRepository {

    override var stopWatcherData: StopWatcherData
        set(value) {
            preferencesStorage.stopWatcherData = value
        }
        get() = preferencesStorage.stopWatcherData
}