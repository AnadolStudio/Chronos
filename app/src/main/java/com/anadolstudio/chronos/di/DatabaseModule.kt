package com.anadolstudio.chronos.di

import android.content.Context
import androidx.room.Room
import com.anadolstudio.data.repository.DataSource
import com.anadolstudio.data.repository.category.CategoryRepositoryImpl
import com.anadolstudio.data.repository.subcategory.SubcategoryRepositoryImpl
import com.anadolstudio.data.repository.subcategory_object.SubcategoryObjectRepositoryImpl
import com.anadolstudio.data.repository.task.TaskRepositoryImpl
import com.anadolstudio.domain.data_source.database.Database
import com.anadolstudio.domain.data_source.database.category.CategoryDao
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryDao
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectDao
import com.anadolstudio.domain.data_source.database.task.TaskDao
import com.anadolstudio.domain.repository.category.CategoryRepository
import com.anadolstudio.domain.repository.subcategory.SubcategoryRepository
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import com.anadolstudio.domain.repository.task.TaskRepository
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): Database = Room
            .databaseBuilder(context, Database::class.java, Database.DATABASE)
            .build()

    @Provides
    fun provideCategoryDao(database: Database): CategoryDao = database.categoryDao

    @Provides
    fun provideSubcategoryDao(database: Database): SubcategoryDao = database.subcategoryDao

    @Provides
    fun provideSubcategoryObjectDao(database: Database): SubcategoryObjectDao = database.subcategoryObjectDao

    @Provides
    fun provideTaskDao(database: Database): TaskDao = database.taskDao


    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
            CategoryRepositoryImpl(categoryDao)

    @Provides
    fun provideSubcategoryRepository(subcategoryDao: SubcategoryDao): SubcategoryRepository =
            SubcategoryRepositoryImpl(subcategoryDao)

    @Provides
    fun provideSubcategoryObjectRepository(subcategoryObjectDao: SubcategoryObjectDao): SubcategoryObjectRepository =
            SubcategoryObjectRepositoryImpl(subcategoryObjectDao)

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository =
            TaskRepositoryImpl(taskDao)

    @Provides
    fun provideDataSource(
            categoryRepository: CategoryRepository,
            subcategoryRepository: SubcategoryRepository,
            subcategoryObjectRepository: SubcategoryObjectRepository,
            taskRepository: TaskRepository
    ): DataSource = DataSource(categoryRepository, subcategoryRepository, subcategoryObjectRepository, taskRepository)

}
