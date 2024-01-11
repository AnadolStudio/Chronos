package com.anadolstudio.data.repository.common

import com.anadolstudio.domain.repository.common.PreferenceRepository
import org.joda.time.DateTime

class PreferenceRepositoryImpl(private val preferencesStorage: PreferencesStorage) : PreferenceRepository {

    override var lastSelectedDate: DateTime
        get() = preferencesStorage.lastSelectedDate
        set(value) {
            preferencesStorage.lastSelectedDate = value
        }

}
