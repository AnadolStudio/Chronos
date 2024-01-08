package com.anadolstudio.chronos.presentation.stopwatcher

import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.track.TrackNavigationArgs
import com.anadolstudio.core.R.string
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import javax.inject.Inject

class StopWatcherViewModel @Inject constructor(
        private val stopWatcherRepository: StopWatcherRepository,
        private val chronosRepository: ChronosRepository,
        private val resources: ResourceRepository,
) : BaseContentViewModel<StopWatcherState>(
        initState = StopWatcherState(
                stopWatcherData = stopWatcherRepository.stopWatcherData
        )
), StopWatcherController {

    init {
        loadCategories()
    }

    private fun loadCategories() = chronosRepository.getAllMainCategories()
            .doOnSubscribe {
                updateState { copy(isLoading = true) }
            }
            .smartSubscribe(
                    onSuccess = { mainCategoryList -> updateState { copy(mainCategoryList = mainCategoryList) } },
                    onError = ::showError,
                    onFinally = { updateState { copy(isLoading = false) } }
            )
            .disposeOnCleared()

    override fun onStopWatcherToggleClicked() {
        when (state.stopWatcherData.state) {
            StopWatcherData.State.DEFAULT -> startStopWatcher()
            StopWatcherData.State.IN_PROGRESS -> stopStopWatcher()
            StopWatcherData.State.RESULT -> Unit
        }
    }

    override fun onTimeTracked() = clearStopWatcher()

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

    override fun onAddButtonClicked() = navigateTo(
            id = R.id.action_stopWatcherFragment_to_trackBottom,
            args = bundleOf(
                    resources.getString(string.data) to TrackNavigationArgs(
                            mainCategories = state.mainCategoryList,
                            hours = state.stopWatcherData.deltaTime?.hours ?: 0,
                            minutes = state.stopWatcherData.deltaTime?.minutes ?: 0,
                            fromStopWatcher = true
                    )
            )
    )

    override fun onRemoveButtonClicked() = clearStopWatcher()

    private fun clearStopWatcher() = updateStopWatcher(StopWatcherData())

    private fun updateStopWatcher(stopWatcherData: StopWatcherData) {
        updateState { copy(stopWatcherData = stopWatcherData) }
        stopWatcherRepository.stopWatcherData = stopWatcherData
    }
}
