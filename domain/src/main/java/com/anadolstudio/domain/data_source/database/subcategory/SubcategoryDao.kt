package com.anadolstudio.domain.data_source.database.subcategory

import androidx.room.*
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity.Companion.SUBCATEGORY_TABLE
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

@Dao
interface SubcategoryDao {

    @Query("SELECT * FROM $SUBCATEGORY_TABLE")
    fun getAllSubcategories(): Single<List<SubcategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubcategory(subcategory: SubcategoryEntity): Completable

    @Query("UPDATE $SUBCATEGORY_TABLE SET name = :name WHERE subcategory_id = :subcategoryId")
    fun updateSubcategory(subcategoryId: UUID, name: String): Single<Int>

    @Query("SELECT * FROM $SUBCATEGORY_TABLE WHERE subcategory_id = :subcategoryId")
    fun getSubcategoryById(subcategoryId: String): Single<SubcategoryEntity>

    @Delete
    fun deleteSubcategory(subcategory: SubcategoryEntity): Completable

}
