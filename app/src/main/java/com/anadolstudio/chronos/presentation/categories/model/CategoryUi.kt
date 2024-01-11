package com.anadolstudio.chronos.presentation.categories.model

import android.os.Parcelable
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CategoryUi(
        val id: UUID,
        val name: String,
        val color: Int,
        val mainCategoryId: UUID,
        val parentCategoryId: UUID?,
        val parentName: String?,
        val hasChild: Boolean,
) : Parcelable {

    constructor(id: UUID, name: String, color: Int, hasChild: Boolean) : this(
            id = id,
            name = name,
            color = color,
            mainCategoryId = id,
            parentCategoryId = null,
            parentName = null,
            hasChild = hasChild,
    )

    @IgnoredOnParcel
    val isMainCategory: Boolean = id == mainCategoryId

    @IgnoredOnParcel
    val isRootCategory: Boolean = mainCategoryId == parentCategoryId
}

fun List<MainCategoryDomain>.toCategoryUi(): List<CategoryUi> = flatMap { it.toCategoryUi() }

fun MainCategoryDomain.toCategoryUi(): List<CategoryUi> = mapInnerCategory(
        CategoryUi(id = uuid, name = name, color = color, hasChild = subcategoryList.isNotEmpty()),
        subcategoryList,
        color
)

private fun mapInnerCategory(mainCategory: CategoryUi, subcategoryList: List<SubcategoryDomain>, color: Int): List<CategoryUi> {
    val subcategory = subcategoryList.flatMap { subcategory ->
        subcategory.getAllSubcategory(mainCategory.name, color)
    }

    return listOf(mainCategory) + subcategory
}

fun SubcategoryDomain.toCategoryUi(parentName: String, color: Int): List<CategoryUi> {
    val rootCategory = CategoryUi(
            id = uuid,
            name = name,
            color = color,
            mainCategoryId = mainCategoryId,
            parentCategoryId = parentCategoryId,
            parentName = parentName,
            hasChild = subcategoryList.isNotEmpty()
    )

    return mapInnerCategory(rootCategory, subcategoryList, color)
}

private fun SubcategoryDomain.getAllSubcategory(parentName: String, color: Int): List<CategoryUi> {
    val innerSubcategory = subcategoryList.flatMap { it.getAllSubcategory(name, color) }

    val category = CategoryUi(
            id = uuid,
            name = name,
            color = color,
            mainCategoryId = mainCategoryId,
            parentCategoryId = parentCategoryId,
            parentName = parentName,
            hasChild = innerSubcategory.isNotEmpty()
    )

    return listOf(category) + innerSubcategory
}
