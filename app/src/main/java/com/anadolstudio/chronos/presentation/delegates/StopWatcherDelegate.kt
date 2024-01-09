package com.anadolstudio.chronos.presentation.delegates

import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository

class StopWatcherDelegate(
        private val provideData: ()-> StopWatcherData,
        private val onDataChange: (StopWatcherData)-> Unit,
        private val stopWatcherRepository: StopWatcherRepository
) {

    fun clearStopWatcher() = updateStopWatcher { StopWatcherData() }

    fun onStopWatcherToggleClicked() = when (provideData.invoke().state) {
        StopWatcherData.State.DEFAULT -> startStopWatcher()
        StopWatcherData.State.IN_PROGRESS -> stopStopWatcher()
        StopWatcherData.State.RESULT -> resumeStopWatcher()
    }

    private fun startStopWatcher() = updateStopWatcher {
        StopWatcherData(startTime = System.currentTimeMillis(), endTime = null)
    }

    private fun resumeStopWatcher() = updateStopWatcher {
        val stopWatcherData = provideData.invoke()
        val delta = (stopWatcherData.endTime ?: 0) - (stopWatcherData.startTime ?: 0)
        StopWatcherData(startTime = System.currentTimeMillis() - delta, endTime = null)
    }

    private fun stopStopWatcher() = updateStopWatcher {
        provideData.invoke().copy(endTime = System.currentTimeMillis())
    }

    private fun updateStopWatcher(provideData: () -> StopWatcherData) {
        val stopWatcherData = provideData.invoke()
        stopWatcherRepository.stopWatcherData = stopWatcherData
        onDataChange.invoke(stopWatcherData)
    }
}
