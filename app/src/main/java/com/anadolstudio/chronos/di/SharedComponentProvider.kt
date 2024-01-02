package com.anadolstudio.chronos.di

import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import dagger.Module
import javax.inject.Singleton

@Singleton
interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
