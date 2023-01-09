package com.anadolstudio.data.repository.subcategory

import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryDao
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.repository.subcategory.SubcategoryRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

class SubcategoryRepositoryImpl(private val subcategoryDao: SubcategoryDao) : SubcategoryRepository {

    override fun getAllSubcategories(): Single<List<SubcategoryEntity>> = subcategoryDao
            .getAllSubcategories()

    override fun insertSubcategory(subcategory: SubcategoryEntity): Completable = subcategoryDao
            .insertSubcategory(subcategory)

    override fun updateSubcategory(subcategoryId: UUID, name: String): Single<Int> = subcategoryDao
            .updateSubcategory(subcategoryId, name)

    override fun getSubcategoryById(subcategoryId: String): Single<SubcategoryEntity> = subcategoryDao
            .getSubcategoryById(subcategoryId)

    override fun deleteSubcategory(subcategory: SubcategoryEntity): Completable = subcategoryDao
            .deleteSubcategory(subcategory)
}
