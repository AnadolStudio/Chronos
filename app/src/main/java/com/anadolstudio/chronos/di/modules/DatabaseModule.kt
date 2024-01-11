package com.anadolstudio.chronos.di.modules

import android.content.Context
import androidx.room.Room
import com.anadolstudio.data.repository.chronos.ChronosRepositoryImpl
import com.anadolstudio.data.repository.chronos.ChronosDatabase
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryDao
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryDao
import com.anadolstudio.data.repository.chronos.track.TrackDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): ChronosDatabase = Room
        .databaseBuilder(context, ChronosDatabase::class.java, ChronosDatabase.DATABASE)
        .build()

    @Provides
    fun provideCategoryDao(database: ChronosDatabase): MainCategoryDao = database.mainCategoryDao

    @Provides
    fun provideSubcategoryDao(database: ChronosDatabase): SubcategoryDao = database.subcategoryDao

    @Provides
    fun provideTaskDao(database: ChronosDatabase): TrackDao = database.trackDao

    @Provides
    fun provideDataSource(
        mainCategoryDao: MainCategoryDao,
        subcategoryDao: SubcategoryDao,
        trackDao: TrackDao
    ): ChronosRepositoryImpl = ChronosRepositoryImpl(
        mainCategoryDao,
        subcategoryDao,
        trackDao
    )

}
