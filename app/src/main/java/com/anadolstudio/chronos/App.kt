package com.anadolstudio.chronos

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.anadolstudio.chronos.di.DI
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.SharedComponentProvider
import com.anadolstudio.data.repository.common.PreferencesStorage
import javax.inject.Inject

class App : Application(), SharedComponentProvider {

    @Inject
    lateinit var preferences: PreferencesStorage

    override fun onCreate() {
        super.onCreate()

        DI.init(applicationContext)
        DI.getComponent().inject(this)
        AppCompatDelegate.setDefaultNightMode(preferences.nightMode)
    }

    override fun getModule(): SharedComponent = DI.getComponent()
}
