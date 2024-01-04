package com.anadolstudio.chronos.di

import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import com.anadolstudio.chronos.presentation.categories.CategoriesViewModel
import dagger.Module
import javax.inject.Singleton

@Singleton
interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun categoriesViewModelFactory(): CategoriesViewModel.Factory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
