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

        DI.init(applicationContext)
    }

}
