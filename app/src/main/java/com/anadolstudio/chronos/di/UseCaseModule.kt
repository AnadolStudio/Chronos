package com.anadolstudio.chronos.di

import com.anadolstudio.domain.data_source.database.SettingsDataSource
import com.anadolstudio.domain.use_case.SettingUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideSettingsUseCase(dataSource: SettingsDataSource): SettingUseCase = SettingUseCase(dataSource)

}
