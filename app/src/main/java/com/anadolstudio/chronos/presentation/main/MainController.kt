package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.ui.viewmodel.BaseController

interface MainController : BaseController {
    fun onCalendarClicked()
    fun onAddClicked()
    fun onDiagramClicked()
    fun onStopWatcherClicked()
    fun onEditItemsClicked()
    fun onTimeTrackChanged()
    fun onTrackClicked(trackRootUi: TrackRootUi)
    fun onDateSelected(dateTime: Long)
    fun onPreviousDateSelected()
    fun onNextDateSelected()
    fun onStopWatcherToggleClicked()
    fun onChangeNightModeClicked()
    fun onCategoriesSelected(categoryUi: CategoryUi)
}
