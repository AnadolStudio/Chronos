package com.anadolstudio.chronos.presentation.stopwatcher

import android.os.SystemClock
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.domain.repository.stop_watcher.PreferenceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import javax.inject.Inject

class StopWatcherViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : BaseContentViewModel<StopWatcherState>(
    initState = StopWatcherState(
        stopWatcherData = preferenceRepository.stopWatcherData
    )
), StopWatcherController {

    override fun onStopWatcherToggleClicked() {
        when (state.stopWatcherData.state) {
            StopWatcherData.State.DEFAULT -> startStopWatcher()
            StopWatcherData.State.IN_PROGRESS -> stopStopWatcher()
            StopWatcherData.State.RESULT -> Unit
        }
    }

    private fun startStopWatcher() {
        val data = StopWatcherData(
            startTime = System.currentTimeMillis(),
            endTime = null
        )
        updateStopWatcher(data)
    }

    private fun stopStopWatcher() {
        val data = state.stopWatcherData.copy(
            endTime = System.currentTimeMillis(),
        )
        updateStopWatcher(data)
    }

    override fun onAddButtonClicked() = showTodo()

    override fun onRemoveButtonClicked() = clearStopWatcher()

    private fun clearStopWatcher() = updateStopWatcher(StopWatcherData())

    private fun updateStopWatcher(stopWatcherData: StopWatcherData) {
        updateState { copy(stopWatcherData = stopWatcherData) }
        preferenceRepository.stopWatcherData = stopWatcherData
    }
}