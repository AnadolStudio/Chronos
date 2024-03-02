package com.anadolstudio.chronos.presentation.stopwatcher

import com.anadolstudio.ui.viewmodel.BaseController

interface StopWatcherController : BaseController {
    fun onStopWatcherToggleClicked()
    fun onAddButtonClicked()
    fun onRemoveButtonClicked()
    fun onTimeTracked()
}
