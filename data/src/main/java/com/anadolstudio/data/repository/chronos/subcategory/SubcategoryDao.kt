package com.anadolstudio.data.repository.chronos.subcategory

import androidx.room.*
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryEntity.Companion.SUBCATEGORY_TABLE
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SubcategoryDao {

    @Query("SELECT * FROM $SUBCATEGORY_TABLE")
    fun getAllSubcategories(): Single<List<SubcategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSubcategory(subcategory: SubcategoryEntity): Completable

    @Update(entity = SubcategoryEntity::class, onConflict = OnConflictStrategy.ABORT)
    fun updateSubcategory(subcategory: SubcategoryEntity): Completable

    @Delete
    fun deleteSubcategory(subcategory: SubcategoryEntity): Completable

}
