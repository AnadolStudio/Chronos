package com.anadolstudio.domain.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anadolstudio.domain.data_source.database.category.CategoryDao
import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryDao
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectDao
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.data_source.database.task.TaskDao
import com.anadolstudio.domain.data_source.database.task.TaskEntity

@Database(
        version = 1,
        entities = [
            CategoryEntity::class,
            SubcategoryEntity::class,
            SubcategoryObjectEntity::class,
            TaskEntity::class
        ],
        exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class Database : RoomDatabase() {

    companion object {
        const val DATABASE = "database"
    }

    abstract val categoryDao: CategoryDao
    abstract val subcategoryDao: SubcategoryDao
    abstract val subcategoryObjectDao: SubcategoryObjectDao
    abstract val taskDao: TaskDao
}
