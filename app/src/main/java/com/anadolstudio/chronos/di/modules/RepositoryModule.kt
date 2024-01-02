package com.anadolstudio.chronos.di.modules

import android.content.Context
import com.anadolstudio.data.repository.preferences.PreferenceRepositoryImpl
import com.anadolstudio.data.repository.preferences.PreferencesStorage
import com.anadolstudio.domain.repository.stop_watcher.PreferenceRepository
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providePreferences(context: Context): Preferences =
        BinaryPreferencesBuilder(context)
            .allowBuildOnBackgroundThread()
            .build()

    @Provides
    fun providePreferencesStorage(preferences: Preferences): PreferencesStorage = PreferencesStorage(preferences)

    @Provides
    fun providePreferencesRepository(preferences: PreferencesStorage): PreferenceRepository =
        PreferenceRepositoryImpl(preferences)

}
