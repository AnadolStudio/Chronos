package com.anadolstudio.chronos.di

import android.content.Context
import com.anadolstudio.chronos.ui.days.DaysFragment
import com.anadolstudio.chronos.ui.settings.SettingsViewModel
import com.anadolstudio.chronos.ui.settings.inner_fragment.EmptySettingsInnerViewModel
import com.anadolstudio.chronos.ui.settings.inner_fragment.SettingsInnerViewModel
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
    fun inject(entry: SettingsViewModel.Factory)
    fun inject(entry: SettingsInnerViewModel.Factory)
    fun inject(entry: EmptySettingsInnerViewModel.Factory)
}
