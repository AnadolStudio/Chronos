package com.anadolstudio.chronos.presentation.create

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class CreateBottomEvents : SingleCustomEvent() {

    data class Result(val category: CategoryUi) : CreateBottomEvents()

}
