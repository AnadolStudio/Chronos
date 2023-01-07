package com.anadolstudio.chronos.di

import android.content.Context
import com.anadolstudio.chronos.ui.days.DaysFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }

    fun inject(entry: DaysFragment)
}
