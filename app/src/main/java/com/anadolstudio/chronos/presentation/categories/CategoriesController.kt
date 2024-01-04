package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.viewmodel.BaseController

interface CategoriesController : BaseController {
    fun onCategoryClicked(categoryUi: CategoryUi)
}
