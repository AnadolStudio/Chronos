package com.anadolstudio.chronos.presentation.create

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.ui.viewmodel.BaseController

interface CreateController : BaseController {
    fun onNameChanged(name: String)
    fun onParentNameChanged(name: String)
    fun onSearchButtonClicked()
    fun onCategoriesSelected(categoryUi: CategoryUi?)
    fun onCreateClicked()
}
