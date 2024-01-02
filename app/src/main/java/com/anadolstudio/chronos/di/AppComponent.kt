package com.anadolstudio.chronos.di

import android.content.Context
import com.anadolstudio.chronos.di.modules.AppModule
import com.anadolstudio.chronos.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : SharedComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }

}
