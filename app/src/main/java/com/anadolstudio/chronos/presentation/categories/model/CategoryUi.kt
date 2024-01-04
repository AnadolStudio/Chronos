package com.anadolstudio.chronos.presentation.categories.model

import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import java.util.UUID

data class CategoryUi(
        val id: UUID,
        val name: String,
        val color: Int,
        val mainCategoryId: UUID,
        val parentCategoryId: UUID?,
        val parentName: String?,
) {

    constructor(id: UUID, name: String, color: Int) : this(
            id = id,
            name = name,
            color = color,
            mainCategoryId = id,
            parentCategoryId = null,
            parentName = null,
    )

    val isMainCategory: Boolean = id == mainCategoryId
    val isRootCategory: Boolean = mainCategoryId == parentCategoryId
}

fun MainCategoryDomain.toCategoryUi(): List<CategoryUi> {
    val mainCategory = CategoryUi(id = uuid, name = name, color = color)

    val subcategory = subcategoryList.flatMap { subcategory ->
        subcategory.getAllSubcategory(name, color)
    }

    return listOf(mainCategory) + subcategory
}

private fun SubcategoryDomain.getAllSubcategory(parentName: String, color: Int): List<CategoryUi> {
    val category = CategoryUi(
            id = uuid,
            name = name,
            color = color,
            mainCategoryId = mainCategoryId,
            parentCategoryId = parentCategoryId,
            parentName = parentName,
    )

    val innerSubcategory = subcategoryList.flatMap { it.getAllSubcategory(name, color) }

    return listOf(category) + innerSubcategory
}
