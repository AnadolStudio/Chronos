package com.anadolstudio.chronos

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.anadolstudio.chronos.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setDefaultNightMode(MODE_NIGHT_NO) // TODO Temp

        DI.init(applicationContext)
    }

    private fun getNightModeFromSystem(): Int = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> MODE_NIGHT_YES
        else -> MODE_NIGHT_NO
    }
}
