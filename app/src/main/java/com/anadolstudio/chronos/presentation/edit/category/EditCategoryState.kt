package com.anadolstudio.chronos.presentation.edit.category

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState

data class EditCategoryState(
        val oldName: String,
        val categoryUi: CategoryUi,
        val categoryState: CategoryState,
        val parentCategoryUi: CategoryUi? = categoryState.idCategoryMap[categoryUi.parentCategoryId],
        val name: String = oldName,
        val parentName: String = "",
        val isLoading: Boolean = false,
) {

    val createEnable: Boolean
        get() {
            val changeName = name.isNotBlank() && categoryState.nameCategoryMap[name] == null
            val changeParent = parentCategoryUi != null && categoryUi.parentCategoryId != parentCategoryUi.id

            return changeName || changeParent
        }

}
