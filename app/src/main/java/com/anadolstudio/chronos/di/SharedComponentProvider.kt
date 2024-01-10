package com.anadolstudio.chronos.di

import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import com.anadolstudio.chronos.presentation.categories.CategoriesViewModel
import com.anadolstudio.chronos.presentation.create.CreateViewModel
import com.anadolstudio.chronos.presentation.edit.category.EditCategoryViewModel
import com.anadolstudio.chronos.presentation.track.TrackViewModel
import dagger.Module

interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun categoriesViewModelFactory(): CategoriesViewModel.Factory
    fun trackViewModelFactory(): TrackViewModel.Factory
    fun createViewModelFactory(): CreateViewModel.Factory
    fun editViewModelFactory(): EditCategoryViewModel.Factory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
