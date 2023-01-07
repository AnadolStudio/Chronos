package com.anadolstudio.data.repository.category

import com.anadolstudio.core.rx_util.schedulersIoToMain
import com.anadolstudio.domain.data_source.database.category.CategoryDao
import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.repository.category.CategoryRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {

    override fun getAllCategories(): Single<List<CategoryEntity>> = categoryDao
            .getAllCategories()
            .schedulersIoToMain()

    override fun insertCategory(category: CategoryEntity): Completable = categoryDao
            .insertCategory(category)
            .schedulersIoToMain()

    override fun updateCategory(categoryId: UUID, name: String, color: Int): Single<Int> = categoryDao
            .updateCategory(categoryId, name, color)
            .schedulersIoToMain()

    override fun getCategoryById(categoryId: String): Single<CategoryEntity> = categoryDao
            .getCategoryById(categoryId)
            .schedulersIoToMain()

    override fun deleteCategory(category: CategoryEntity): Completable = categoryDao
            .deleteCategory(category)
            .schedulersIoToMain()
}
