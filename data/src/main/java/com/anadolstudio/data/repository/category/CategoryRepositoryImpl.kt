package com.anadolstudio.data.repository.category

import com.anadolstudio.domain.data_source.database.category.CategoryDao
import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.repository.category.CategoryRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {

    override fun getAllCategories(): Single<List<CategoryEntity>> = categoryDao
            .getAllCategories()

    override fun insertCategory(category: CategoryEntity): Completable = categoryDao
            .insertCategory(category)

    override fun updateCategory(categoryId: UUID, name: String, color: Int): Single<Int> = categoryDao
            .updateCategory(categoryId, name, color)

    override fun getCategoryById(categoryId: String): Single<CategoryEntity> = categoryDao
            .getCategoryById(categoryId)

    override fun deleteCategory(category: CategoryEntity): Completable = categoryDao
            .deleteCategory(category)
}
