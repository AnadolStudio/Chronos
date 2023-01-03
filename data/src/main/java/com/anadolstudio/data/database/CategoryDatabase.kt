package com.anadolstudio.data.database

import androidx.room.*
import com.anadolstudio.data.data.Category

@Dao
interface CategoryDatabase {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCategory(category: Category)

    @Query("SELECT * FROM ${Category.CATEGORY_TABLE}")
    fun getAllCategories(): List<Category>

    @Query("SELECT * FROM ${Category.CATEGORY_TABLE} WHERE category_id = :categoryId")
    fun getCategory(categoryId: String): Category

    @Delete
    fun deleteCategory(category: Category)

}
