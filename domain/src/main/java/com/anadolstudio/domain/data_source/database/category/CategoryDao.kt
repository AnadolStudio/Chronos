package com.anadolstudio.domain.data_source.database.category

import androidx.room.*
import com.anadolstudio.domain.data_source.database.category.CategoryEntity.Companion.CATEGORY_TABLE
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun getAllCategories(): Single<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: CategoryEntity): Completable

    @Query("UPDATE $CATEGORY_TABLE SET name = :name, color = :color WHERE category_id = :categoryId")
    fun updateCategory(categoryId: UUID, name: String, color: Int): Single<Int>

    @Query("SELECT * FROM $CATEGORY_TABLE WHERE category_id = :categoryId")
    fun getCategoryById(categoryId: String): Single<CategoryEntity>

    @Delete
    fun deleteCategory(category: CategoryEntity): Completable

}
