package com.anadolstudio.data.repository

import com.anadolstudio.core.rx_util.singleFrom
import com.anadolstudio.domain.data_source.database.SettingsDataSource
import com.anadolstudio.domain.mapper.SettingsDataSourceMapper
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.repository.category.CategoryRepository
import com.anadolstudio.domain.repository.subcategory.SubcategoryRepository
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import com.anadolstudio.domain.repository.task.TaskRepository
import io.reactivex.Completable
import io.reactivex.Observable

class SettingsDataSourceImpl constructor(
        private val mapper: SettingsDataSourceMapper,
        categoryRepository: CategoryRepository,
        subcategoryRepository: SubcategoryRepository,
        subcategoryObjectRepository: SubcategoryObjectRepository,
        taskRepository: TaskRepository
) : SettingsDataSource, CategoryRepository by categoryRepository,
        SubcategoryRepository by subcategoryRepository,
        SubcategoryObjectRepository by subcategoryObjectRepository,
        TaskRepository by taskRepository {

    override fun getAllCategoriesWithInnerData(): Observable<List<SettingsCategoryModel.Category>> = Observable.zip(
            getAllCategories().toObservable(),
            getAllSubcategories().toObservable(),
            getAllObjects().toObservable(),
            mapper::mapOut
    )

    override fun addCategoryWithInnerData(category: SettingsCategoryModel.Category): Completable = singleFrom { mapper.mapInto(category) }
            .toObservable()
            .flatMapCompletable { mappedData ->
                Completable.concatArray(
                        insertCategory(mappedData.categoryEntity),
                        *mappedData.subcategoryEntityList.map(this::insertSubcategory).toTypedArray(),
                        *mappedData.subcategoryObjectEntityList.map(this::insertObject).toTypedArray()
                )
            }
}
