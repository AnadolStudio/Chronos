package com.anadolstudio.chronos.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadolstudio.chronos.di.viewmodel.ViewModelFactory
import com.anadolstudio.chronos.di.viewmodel.ViewModelKey
import com.anadolstudio.chronos.presentation.activity.single.SingleViewModel
import com.anadolstudio.chronos.presentation.main.MainViewModel
import com.anadolstudio.chronos.presentation.stopwatcher.StopWatcherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SingleViewModel::class)
    fun bindSingleViewModel(viewModel: SingleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StopWatcherViewModel::class)
    fun bindStopWatcherViewModel(viewModel: StopWatcherViewModel): ViewModel
}
