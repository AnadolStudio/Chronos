package com.anadolstudio.chronos.presentation.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.delegates.StopWatcherDelegate
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.chronos.presentation.main.model.toTrackRootUi
import com.anadolstudio.chronos.presentation.track.TrackNavigationArgs
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.chronos.util.plusDay
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.common.NightModeRepository
import com.anadolstudio.domain.repository.common.PreferenceRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val chronosRepository: ChronosRepository,
        private val resources: ResourceRepository,
        private val preferenceRepository: PreferenceRepository,
        private val nightModeRepository: NightModeRepository,
        private val stopWatcherRepository: StopWatcherRepository,
) : BaseContentViewModel<MainState>(
        initState = MainState(
                isNightMode = nightModeRepository.nightMode == AppCompatDelegate.MODE_NIGHT_YES,
                currentDate = preferenceRepository.lastSelectedDate,
                stopWatcherData = stopWatcherRepository.stopWatcherData,
                stopWatcherTime = stopWatcherRepository.currentDelta
        )
), MainController {

    private companion object {
        const val STOP_WATCHER_INTERVAL = 1L
    }

    private val stopWatcherDelegate: StopWatcherDelegate = StopWatcherDelegate(
            provideData = { state.stopWatcherData },
            onDataChange = { updateState { copy(stopWatcherData = it) } },
            stopWatcherRepository = stopWatcherRepository,
    )

    init {
        loadAllData()
        observeStopWatcher()
        observeNightMode()
    }

    private fun observeNightMode() {
        nightModeRepository.observeNightModeChanges()
                .filter { it == AppCompatDelegate.MODE_NIGHT_YES || it == AppCompatDelegate.MODE_NIGHT_NO }
                .map { it == AppCompatDelegate.MODE_NIGHT_YES }
                .smartSubscribe(
                        onSuccess = { isNightMode -> updateState { copy(isNightMode = isNightMode) } },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    private fun observeStopWatcher() {
        stopWatcherRepository.observeStopWatcherChanges()
                .smartSubscribe(
                        onSuccess = { updateState { copy(stopWatcherData = it) } },
                        onError = ::showError
                )
                .disposeOnCleared()

        Observable.interval(STOP_WATCHER_INTERVAL, TimeUnit.SECONDS)
                .smartSubscribe(
                        onSuccess = {
                            updateState { copy(stopWatcherTime = stopWatcherRepository.currentDelta) }
                        },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    private fun initMainCategoriesIfNeed(mainCategoryList: List<MainCategoryDomain>) {
        if (mainCategoryList.isNotEmpty()) return
        initMainCategories()
    }

    override fun onTimeTracked() = loadAllData()

    private fun initMainCategories() = with(resources) {
        Completable.concatArray(
                addMainCategory(getString(R.string.global_body), getColor(R.color.bodyColor)),
                addMainCategory(getString(R.string.global_mind), getColor(R.color.mindColor)),
                addMainCategory(getString(R.string.global_emotions), getColor(R.color.emotionsColor)),
                addMainCategory(getString(R.string.global_consciousness), getColor(R.color.consciousnessColor))
        ).smartSubscribe(
                onSubscribe = { updateState { copy(isLoading = true) } },
                onComplete = this@MainViewModel::loadAllData,
                onError = this@MainViewModel::showError,
                onFinally = { updateState { copy(isLoading = false) } }
        ).disposeOnCleared()
    }

    private fun loadAllData() {
        Single.zip(
                chronosRepository.getAllMainCategories(),
                chronosRepository.getTrackListByDate(state.trackState.currentDate)
                        .map { trackList -> trackList.associateBy { it.subcategoryId } }
        ) { mainCategoryList, trackMap ->
            val trackRootList = mainCategoryList.map { it.toTrackRootUi(trackMap) }

            return@zip mainCategoryList to trackRootList
        }.smartSubscribe(
                onSubscribe = { updateState { copy(isLoading = true) } },
                onSuccess = { (mainCategoryList, trackRootList) ->
                    updateState {
                        copy(
                                categoryState = categoryState.copy(mainCategoryList = mainCategoryList),
                                trackState = trackState.copy(trackRootList = trackRootList)
                        )
                    }
                    initMainCategoriesIfNeed(mainCategoryList)
                },
                onError = this::showError,
                onFinally = { updateState { copy(isLoading = false) } }
        ).disposeOnCleared()
    }

    private fun addMainCategory(name: String, color: Int): Completable = chronosRepository
            .insertMainCategory(MainCategoryDomain(name = name, color = color))

    override fun onCalendarClicked() = showEvent(
            MainEvents.ShowCalendar(currentDateTime = state.trackState.currentDate)
    )

    override fun onAddClicked() = navigateTo(
            id = R.id.action_mainFragment_to_trackBottom,
            args = bundleOf(
                    resources.getString(com.anadolstudio.core.R.string.data) to TrackNavigationArgs(
                            mainCategories = state.categoryState.mainCategoryList,
                            selectedDateTime = state.trackState.currentDate
                    )
            )
    )

    override fun onDiagramClicked() = showTodo()

    override fun onStopWatcherClicked() = navigateTo(R.id.action_mainFragment_to_stopWatcherFragment)

    override fun onEditItemsClicked() = showTodo()

    override fun onTrackClicked(trackRootUi: TrackRootUi) = showTodo()

    override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        changeCurrentDate(DateTime(year, month, dayOfMonth, 0, 0))
    }

    override fun onPreviousDateSelected() = changeCurrentDate(state.trackState.currentDate.minusDay())

    override fun onNextDateSelected() = changeCurrentDate(state.trackState.currentDate.plusDay())

    private fun changeCurrentDate(currentDate: DateTime) {
        if (currentDate.isAfter(DateTime.now().withTimeAtStartOfDay())) return

        preferenceRepository.lastSelectedDate = currentDate
        updateState { copy(trackState = trackState.copy(currentDate = currentDate)) }
        loadAllData()
    }

    override fun onStopWatcherToggleClicked() = stopWatcherDelegate.onStopWatcherToggleClicked()

    override fun onChangeNightModeClicked() = nightModeRepository.toggleNightMode()
}
