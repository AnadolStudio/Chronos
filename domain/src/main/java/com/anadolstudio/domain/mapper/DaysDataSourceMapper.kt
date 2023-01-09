package com.anadolstudio.domain.mapper

import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.models.days.DaysCategoryModel

interface DaysDataSourceMapper : DataSourceMapper<DaysCategoryModel.Category> {

    override fun mapOut(
            categoryEntityList: List<CategoryEntity>,
            subcategoryEntityList: List<SubcategoryEntity>,
            subcategoryObjectEntityList: List<SubcategoryObjectEntity>
    ): List<DaysCategoryModel.Category>

}
