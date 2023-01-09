package com.anadolstudio.data.mapper.settings

import com.anadolstudio.domain.data_source.database.category.CategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory.SubcategoryEntity
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.mapper.DataSourceMapper
import com.anadolstudio.domain.mapper.SettingsDataSourceMapper
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel.Subcategory
import com.anadolstudio.domain.models.settings.SettingsCategoryModel.SubcategoryObject

class SettingsDataSourceMapperImpl : SettingsDataSourceMapper {

    override fun mapOut(
            categoryEntityList: List<CategoryEntity>,
            subcategoryEntityList: List<SubcategoryEntity>,
            subcategoryObjectEntityList: List<SubcategoryObjectEntity>
    ): List<SettingsCategoryModel.Category> {
        val result = mutableListOf<SettingsCategoryModel.Category>()

        categoryEntityList.forEach { categoryEntity ->
            result.add(
                    SettingsCategoryModel.Category(
                            uuid = categoryEntity.categoryId,
                            title = categoryEntity.name,
                            color = categoryEntity.color,
                            subcategoriesList = subcategoryEntityList
                                    .filter { it.categoryId == categoryEntity.categoryId }
                                    .map { subcategoryEntity -> subcategoryEntity.toSubcategory(subcategoryObjectEntityList) }
                    )
            )
        }

        return result
    }

    private fun SubcategoryEntity.toSubcategory(subcategoryObjectEntityList: List<SubcategoryObjectEntity>): Subcategory =
            Subcategory(
                    uuid = this.subcategoryId,
                    title = this.name,
                    objectsList = subcategoryObjectEntityList
                            .filter { it.subcategoryId == this.subcategoryId }
                            .map { subcategoryEntityObject -> subcategoryEntityObject.toSubcategoryObject() }
            )

    private fun SubcategoryObjectEntity.toSubcategoryObject(): SubcategoryObject =
            SubcategoryObject(
                    uuid = this.objectId,
                    title = this.name,
            )

    override fun mapInto(category: SettingsCategoryModel.Category): DataSourceMapper.CategoryMapIntoData {
        val categoryEntity = CategoryEntity(
                categoryId = category.uuid,
                name = category.title,
                color = category.color
        )

        val subcategoryEntityList = mutableListOf<SubcategoryEntity>()
        val subcategoryObjectEntityList = mutableListOf<SubcategoryObjectEntity>()

        category.subcategoriesList.forEach { subcategory ->
            subcategoryEntityList.add(
                    SubcategoryEntity(
                            categoryId = category.uuid,
                            subcategoryId = subcategory.uuid,
                            name = subcategory.title,
                    )
            )

            subcategory.objectsList.forEach { subcategoryObject ->
                subcategoryObjectEntityList.add(
                        SubcategoryObjectEntity(
                                categoryId = category.uuid,
                                subcategoryId = subcategory.uuid,
                                objectId = subcategoryObject.uuid,
                                name = subcategoryObject.title,
                        )
                )
            }
        }

        return DataSourceMapper.CategoryMapIntoData(
                categoryEntity = categoryEntity,
                subcategoryEntityList = subcategoryEntityList,
                subcategoryObjectEntityList = subcategoryObjectEntityList
        )
    }
}

