package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.core.viewmodel.BaseController

interface MainController : BaseController {
    fun onCalendarClicked()
    fun onAddClicked()
    fun onDiagramClicked()
    fun onStopWatcherClicked()
    fun onEditItemsClicked()
    fun onTimeTracked()
    fun onTrackClicked(trackRootUi: TrackRootUi)
    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int)
    fun onPreviousDateSelected()
    fun onNextDateSelected()
    fun onStopWatcherToggleClicked()
    fun onChangeNightModeClicked()
}
