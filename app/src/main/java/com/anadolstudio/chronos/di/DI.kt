package com.anadolstudio.chronos.di

import android.content.Context

object DI {

    lateinit var appComponent: AppComponent

    fun init(context: Context) {
        appComponent = DaggerAppComponent.builder()
                .appContext(context)
                .build()
    }
}
