package com.anadolstudio.data.repository.common

import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.anadolstudio.core.util.night_mode.getCurrentNightMode
import com.anadolstudio.domain.repository.common.NightModeRepository
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor

class NightModeRepositoryImpl(
        private val resources: Resources,
        private val preferencesStorage: PreferencesStorage
) : NightModeRepository {

    private val nightModeChanges: BehaviorProcessor<Int> = BehaviorProcessor.create()

    override var nightMode: Int
        get() = preferencesStorage.nightMode
        set(value) {
            preferencesStorage.nightMode = value
            nightModeChanges.onNext(value)
        }

    override fun toggleNightMode() {
        val mode = when (getCurrentNightMode(resources) == AppCompatDelegate.MODE_NIGHT_NO) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        }

        nightMode = mode
    }

    override fun observeNightModeChanges(): Observable<Int> = nightModeChanges.toObservable()
}
