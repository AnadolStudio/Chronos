package com.anadolstudio.domain.repository.subcategory

import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

interface SubcategoryRepository {

    fun getAllSubcategories(): Single<List<SubcategoryEntity>>

    fun insertSubcategory(subcategory: SubcategoryEntity): Completable

    fun updateSubcategory(subcategoryId: UUID, name: String): Single<Int>

    fun getSubcategoryById(subcategoryId: String): Single<SubcategoryEntity>

    fun deleteSubcategory(subcategory: SubcategoryEntity): Completable

}
