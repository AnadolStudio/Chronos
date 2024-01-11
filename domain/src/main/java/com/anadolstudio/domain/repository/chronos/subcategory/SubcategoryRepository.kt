package com.anadolstudio.domain.repository.chronos.subcategory

import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

interface SubcategoryRepository {

    fun getAllSubcategories(): Single<List<SubcategoryDomain>>

    fun insertSubcategory(subcategory: SubcategoryDomain): Completable

    fun updateSubcategory(subcategory: SubcategoryDomain): Completable

    fun deleteSubcategoryById(id: UUID): Completable

    fun getSubcategoryById(id: UUID): Single<SubcategoryDomain>
}
