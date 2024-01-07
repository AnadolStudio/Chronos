package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.viewmodel.BaseController

interface TrackController : BaseController {
    fun onMinutesChanged(minutes: String)
    fun onHoursChanged(hours: String)
    fun onNameChanged(name: String)
    fun onSearchButtonClicked()
    fun onCategoriesSelected(categoryUi: CategoryUi)
    fun onTrackClicked()
    fun onCategoryCreated(categoryUi: CategoryUi)
}
