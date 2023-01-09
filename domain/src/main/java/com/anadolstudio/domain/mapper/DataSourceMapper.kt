package com.anadolstudio.domain.mapper

import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity

interface DataSourceMapper<MapData> {

    fun mapOut(
            categoryEntityList: List<CategoryEntity>,
            subcategoryEntityList: List<SubcategoryEntity>,
            subcategoryObjectEntityList: List<SubcategoryObjectEntity>
    ): List<MapData>

    fun mapInto(
            category: MapData,
    ): CategoryMapIntoData

    data class CategoryMapIntoData(
            val categoryEntity: CategoryEntity,
            val subcategoryEntityList: List<SubcategoryEntity>,
            val subcategoryObjectEntityList: List<SubcategoryObjectEntity>
    )
}
