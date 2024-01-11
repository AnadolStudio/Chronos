package com.anadolstudio.domain.repository.chronos.main_category

import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

interface MainCategoryRepository {

    fun getAllMainCategories(): Single<List<MainCategoryDomain>>

    fun insertMainCategory(category: MainCategoryDomain): Completable

    fun updateMainCategory(category: MainCategoryDomain): Completable

    fun deleteMainCategory(category: MainCategoryDomain): Completable

    fun getMainCategoryById(id: UUID): Single<MainCategoryDomain>

}
