package com.anadolstudio.data.repository.chronos

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryDao
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryEntity
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryDao
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryEntity
import com.anadolstudio.data.repository.chronos.track.TrackDao
import com.anadolstudio.data.repository.chronos.track.TrackEntity

@Database(
        version = 1,
        entities = [
            MainCategoryEntity::class,
            SubcategoryEntity::class,
            TrackEntity::class
        ],
        exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class ChronosDatabase : RoomDatabase() {

    companion object {
        const val DATABASE = "chronos_database"
    }

    abstract val mainCategoryDao: MainCategoryDao
    abstract val subcategoryDao: SubcategoryDao
    abstract val trackDao: TrackDao
}
