package com.anadolstudio.domain.repository.chronos.main_category

import io.reactivex.Completable
import io.reactivex.Single

interface MainCategoryRepository {

    fun getAllMainCategories(): Single<List<MainCategoryDomain>>

    fun insertMainCategory(category: MainCategoryDomain): Completable

    fun updateMainCategory(category: MainCategoryDomain): Completable

    fun deleteMainCategory(category: MainCategoryDomain): Completable

}
