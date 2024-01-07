package com.anadolstudio.chronos.presentation.create

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import java.util.UUID

data class CreateState(
        val categoryList: List<CategoryUi>,
        val nameCategoryMap: Map<String, CategoryUi> = categoryList.associateBy { it.name },
        val idCategoryMap: Map<UUID, CategoryUi> = categoryList.associateBy { it.id },
        val parentCategoryUi: CategoryUi? = null,
        val name: String = "",
        val parentName: String = "",
        val isLoading: Boolean = false,
) {

    val createEnable:Boolean = parentCategoryUi != null && name.isNotBlank() && nameCategoryMap[name] == null

}
