package com.anadolstudio.domain.data_source.database

import com.anadolstudio.domain.repository.category.CategoryRepository
import com.anadolstudio.domain.repository.subcategory.SubcategoryRepository
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import com.anadolstudio.domain.repository.task.TaskRepository
import io.reactivex.Completable
import io.reactivex.Observable

interface DataSource<MappedData> : CategoryRepository, SubcategoryRepository, SubcategoryObjectRepository, TaskRepository{

    fun getAllCategoriesWithInnerData(): Observable<List<MappedData>>

    fun addCategoryWithInnerData(category: MappedData): Completable

}
