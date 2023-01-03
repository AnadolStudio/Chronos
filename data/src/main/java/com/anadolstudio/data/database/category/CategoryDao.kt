package com.anadolstudio.data.database.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.data.database.category.CategoryEntity.Companion.CATEGORY_TABLE
import java.util.UUID

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: CategoryEntity)

    @Query("UPDATE $CATEGORY_TABLE SET name = :name, color = :color WHERE category_id = :categoryId")
    fun updateCategory(categoryId: UUID, name: String, color: Int): Int

    @Query("SELECT * FROM $CATEGORY_TABLE WHERE category_id = :categoryId")
    fun getCategoryById(categoryId: String): CategoryEntity

    @Delete
    fun deleteCategory(category: CategoryEntity)

}
