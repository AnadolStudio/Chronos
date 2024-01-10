package com.anadolstudio.chronos.presentation.stopwatcher

import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.delegates.StopWatcherDelegate
import com.anadolstudio.chronos.presentation.track.TrackNavigationArgs
import com.anadolstudio.core.R.string
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import org.joda.time.DateTime
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

    private val stopWatcherDelegate: StopWatcherDelegate = StopWatcherDelegate(
            provideData = { state.stopWatcherData },
            onDataChange = { updateState { copy(stopWatcherData = it) } },
            stopWatcherRepository = stopWatcherRepository,
    )

    init {
        loadCategories()
    }

    private fun loadCategories() = chronosRepository.getAllMainCategories()
            .smartSubscribe(
                    onSubscribe = { updateState { copy(isLoading = true) } },
                    onSuccess = { mainCategoryList -> updateState { copy(mainCategoryList = mainCategoryList) } },
                    onError = ::showError,
                    onFinally = { updateState { copy(isLoading = false) } }
            )
            .disposeOnCleared()

    override fun onTimeTracked() = stopWatcherDelegate.clearStopWatcher()

    override fun onStopWatcherToggleClicked() = stopWatcherDelegate.onStopWatcherToggleClicked()

    override fun onAddButtonClicked() = navigateTo(
            id = R.id.action_stopWatcherFragment_to_trackBottom,
            args = bundleOf(
                    resources.getString(string.data) to TrackNavigationArgs(
                            mainCategories = state.mainCategoryList,
                            hours = state.stopWatcherData.deltaTime?.hours ?: 0,
                            minutes = state.stopWatcherData.deltaTime?.minutes ?: 0,
                            fromStopWatcher = true,
                            selectedDateTime = DateTime.now().withTimeAtStartOfDay()
                    )
            )
    )

    override fun onRemoveButtonClicked() = stopWatcherDelegate.clearStopWatcher()

}
