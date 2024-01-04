package com.anadolstudio.chronos.di

import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import com.anadolstudio.chronos.presentation.categories.CategoriesViewModel
import com.anadolstudio.chronos.presentation.track.TrackBottom
import com.anadolstudio.chronos.presentation.track.TrackViewModel
import dagger.Module
import javax.inject.Singleton

@Singleton
interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun categoriesViewModelFactory(): CategoriesViewModel.Factory
    fun trackViewModelFactory(): TrackViewModel.Factory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
