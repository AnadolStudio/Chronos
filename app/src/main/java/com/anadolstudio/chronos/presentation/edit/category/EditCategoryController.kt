package com.anadolstudio.chronos.presentation.edit.category

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.ui.viewmodel.BaseController

interface EditCategoryController : BaseController {
    fun onNameChanged(name: String)
    fun onParentNameChanged(name: String)
    fun onSearchButtonClicked()
    fun onCategoriesSelected(categoryUi: CategoryUi?)
    fun onEditClicked()
    fun onRemoveClicked()
    fun onConfirmRemoveClicked()
}
