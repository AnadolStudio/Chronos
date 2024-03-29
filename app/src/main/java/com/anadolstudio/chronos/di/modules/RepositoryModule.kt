package com.anadolstudio.chronos.di.modules

import android.content.Context
import android.content.res.Resources
import com.anadolstudio.data.repository.chronos.ChronosRepositoryImpl
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryDao
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryDao
import com.anadolstudio.data.repository.chronos.track.TrackDao
import com.anadolstudio.data.repository.common.NightModeRepositoryImpl
import com.anadolstudio.data.repository.common.PreferenceRepositoryImpl
import com.anadolstudio.data.repository.common.PreferencesStorage
import com.anadolstudio.data.repository.common.ResourceRepositoryImpl
import com.anadolstudio.data.repository.stop_watcher.StopWatcherRepositoryImpl
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.NightModeRepository
import com.anadolstudio.domain.repository.common.PreferenceRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
        includes = [
            DatabaseModule::class
        ]
)
class RepositoryModule {

    @Provides
    fun providePreferences(context: Context): Preferences =
            BinaryPreferencesBuilder(context)
                    .allowBuildOnBackgroundThread()
                    .build()

    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    fun providePreferencesStorage(preferences: Preferences): PreferencesStorage = PreferencesStorage(preferences)

    @Provides
    @Singleton
    fun provideStopWatcherRepository(preferences: PreferencesStorage): StopWatcherRepository =
            StopWatcherRepositoryImpl(preferences)

    @Provides
    @Singleton
    fun provideNightModeRepository(resources: Resources, preferences: PreferencesStorage): NightModeRepository =
            NightModeRepositoryImpl(resources, preferences)

    @Provides
    fun provideChronosRepository(
            mainCategoryDao: MainCategoryDao,
            subcategoryDao: SubcategoryDao,
            trackDao: TrackDao
    ): ChronosRepository = ChronosRepositoryImpl(
            mainCategoryDao,
            subcategoryDao,
            trackDao,
    )

    @Provides
    fun provideResourceRepository(context: Context): ResourceRepository = ResourceRepositoryImpl(context)

    @Provides
    fun providePreferenceRepositoryImpl(preferences: PreferencesStorage): PreferenceRepository = PreferenceRepositoryImpl(preferences)

}
