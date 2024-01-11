package com.anadolstudio.chronos.di

import android.content.Context

object DI {

    private lateinit var appComponent: AppComponent

    fun init(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appContext(context)
            .build()
    }

    fun getComponent(): AppComponent = appComponent

}
