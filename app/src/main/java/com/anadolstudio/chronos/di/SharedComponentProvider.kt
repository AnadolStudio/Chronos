package com.anadolstudio.chronos.di

import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import com.anadolstudio.chronos.presentation.calendar.CalendarViewModel
import com.anadolstudio.chronos.presentation.categories.CategoriesViewModel
import com.anadolstudio.chronos.presentation.create.CreateViewModel
import com.anadolstudio.chronos.presentation.detail.track.TrackDetailViewModel
import com.anadolstudio.chronos.presentation.edit.category.EditCategoryViewModel
import com.anadolstudio.chronos.presentation.edit.track.EditTrackViewModel
import com.anadolstudio.chronos.presentation.statistic.StatisticViewModel
import com.anadolstudio.chronos.presentation.track.TrackViewModel
import dagger.Module

interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun categoriesViewModelFactory(): CategoriesViewModel.Factory
    fun trackViewModelFactory(): TrackViewModel.Factory
    fun editTrackViewModelFactory(): EditTrackViewModel.Factory
    fun createViewModelFactory(): CreateViewModel.Factory
    fun calendarViewModelFactory(): CalendarViewModel.Factory
    fun editViewModelFactory(): EditCategoryViewModel.Factory
    fun trackDetailViewModelFactory(): TrackDetailViewModel.Factory
    fun statisticViewModelFactory(): StatisticViewModel.Factory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
