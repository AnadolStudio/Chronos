package com.anadolstudio.chronos.presentation.common

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import java.util.UUID

data class CategoryState(val categoryList: List<CategoryUi>) {

    companion object {
        @JvmName("fromMainCategoryList")
        operator fun invoke(mainCategoryList: List<MainCategoryDomain>) = CategoryState(mainCategoryList.toCategoryUi())
    }

    val nameCategoryMap: Map<String, CategoryUi> = categoryList.associateBy { it.name }
    val idCategoryMap: Map<UUID, CategoryUi> = categoryList.associateBy { it.id }
    val childCategoryList: List<CategoryUi> = categoryList.filter { !it.hasChild }
    val childNameCategoryMap: Map<String, CategoryUi> = nameCategoryMap.filter { (_, category) -> !category.hasChild }
}
