package com.anadolstudio.data.database.subcategory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.data.database.subcategory.SubcategoryEntity.Companion.SUBCATEGORY_TABLE
import java.util.UUID

@Dao
interface SubcategoryDao {

    @Query("SELECT * FROM $SUBCATEGORY_TABLE")
    fun getAllSubcategories(): List<SubcategoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubcategory(subcategory: SubcategoryEntity)

    @Query("UPDATE $SUBCATEGORY_TABLE SET name = :name WHERE subcategory_id = :subcategoryId")
    fun updateSubcategory(subcategoryId: UUID, name: String): Int

    @Query("SELECT * FROM $SUBCATEGORY_TABLE WHERE subcategory_id = :subcategoryId")
    fun getSubcategoryById(subcategoryId: String): SubcategoryEntity

    @Delete
    fun deleteSubcategory(subcategory: SubcategoryEntity)

}
