package com.anadolstudio.domain.repository.category

import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

interface CategoryRepository {

    fun getAllCategories(): Single<List<CategoryEntity>>

    fun insertCategory(category: CategoryEntity): Completable

    fun updateCategory(categoryId: UUID, name: String, color: Int): Single<Int>

    fun getCategoryById(categoryId: String): Single<CategoryEntity>

    fun deleteCategory(category: CategoryEntity): Completable

}
