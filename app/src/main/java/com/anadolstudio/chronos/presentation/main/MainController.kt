package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.core.viewmodel.BaseController

interface MainController : BaseController {
    fun onCalendarClicked()
    fun onAddClicked()
    fun onChartClicked()
    fun onStopWatcherClicked()
    fun onEditItemsClicked()
    fun onTimeTracked()
}