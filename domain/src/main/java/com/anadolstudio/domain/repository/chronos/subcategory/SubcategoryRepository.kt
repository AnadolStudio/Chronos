package com.anadolstudio.domain.repository.chronos.subcategory

import io.reactivex.Completable
import io.reactivex.Single

interface SubcategoryRepository {

    fun getAllSubcategories(): Single<List<SubcategoryDomain>>

    fun insertSubcategory(subcategory: SubcategoryDomain): Completable

    fun updateSubcategory(subcategory: SubcategoryDomain): Completable

    fun deleteSubcategory(subcategory: SubcategoryDomain): Completable

}
