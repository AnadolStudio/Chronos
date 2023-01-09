package com.anadolstudio.domain.use_case

import com.anadolstudio.domain.data_source.database.SettingsDataSource
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class SettingUseCase(private val dataSource: SettingsDataSource) {

    fun getAllCategoriesWithInnerData(): Observable<List<SettingsCategoryModel.Category>> = dataSource.getAllCategoriesWithInnerData()

    fun addCategoryWithInnerData(category: SettingsCategoryModel.Category): Completable = dataSource.addCategoryWithInnerData(category)

    fun getAllSubcategories(): Single<List<SubcategoryEntity>> = dataSource.getAllSubcategories()

}
