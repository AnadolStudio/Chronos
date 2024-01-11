package com.anadolstudio.chronos.presentation.track.base

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.viewmodel.BaseController

interface BaseTrackController : BaseController {
    fun onMinutesChanged(minutes: String)
    fun onHoursChanged(hours: String)
    fun onNameChanged(name: String)
    fun onSearchButtonClicked()
    fun onCategoriesSelected(categoryUi: CategoryUi)
    fun onTrackClicked()
}
