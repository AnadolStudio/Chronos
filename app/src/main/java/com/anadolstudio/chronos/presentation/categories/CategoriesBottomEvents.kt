package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class CategoriesBottomEvents : SingleCustomEvent() {

    data class Result(val categoryUi: CategoryUi) : CategoriesBottomEvents()

}
