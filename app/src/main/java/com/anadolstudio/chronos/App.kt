package com.anadolstudio.chronos

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setDefaultNightMode(MODE_NIGHT_NO) // TODO Temp
    }

    private fun getNightModeFromSystem(): Int = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> MODE_NIGHT_YES
        else -> MODE_NIGHT_NO
    }
}
