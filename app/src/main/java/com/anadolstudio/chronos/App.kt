package com.anadolstudio.chronos

import android.app.Application
import com.anadolstudio.chronos.di.DI
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.SharedComponentProvider

class App : Application(), SharedComponentProvider {

    override fun onCreate() {
        super.onCreate()

        DI.init(applicationContext)
    }

    override fun getModule(): SharedComponent = DI.getComponent()
}
