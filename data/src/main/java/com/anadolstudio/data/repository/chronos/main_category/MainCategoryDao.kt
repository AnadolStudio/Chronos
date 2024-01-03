package com.anadolstudio.data.repository.chronos.main_category

import androidx.room.*
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryEntity.Companion.CATEGORY_TABLE
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MainCategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun getAllMainCategories(): Single<List<MainCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMainCategory(category: MainCategoryEntity): Completable

    @Update(entity = MainCategoryEntity::class)
    fun updateMainCategory(mainCategoryEntity: MainCategoryEntity): Completable

    @Delete
    fun deleteMainCategory(category: MainCategoryEntity): Completable

}
