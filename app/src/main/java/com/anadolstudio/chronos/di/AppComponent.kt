package com.anadolstudio.chronos.di

import android.content.Context
import com.anadolstudio.chronos.di.modules.AppModule
import com.anadolstudio.chronos.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent : SharedComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }

}
