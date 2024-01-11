package com.anadolstudio.chronos.presentation.stopwatcher

import com.anadolstudio.core.viewmodel.BaseController

interface StopWatcherController : BaseController {
    fun onStopWatcherToggleClicked()
    fun onAddButtonClicked()
    fun onRemoveButtonClicked()
    fun onTimeTracked()
}