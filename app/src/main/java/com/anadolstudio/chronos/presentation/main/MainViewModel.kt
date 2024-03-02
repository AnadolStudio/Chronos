package com.anadolstudio.chronos.presentation.main

import androidx.appcompat.app.AppCompatDelegate
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.calendar.CalendarNavigationArgs
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.delegates.StopWatcherDelegate
import com.anadolstudio.chronos.presentation.detail.track.TrackDetailNavigationArgs
import com.anadolstudio.chronos.presentation.edit.category.EditCategoryNavigationArgs
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.chronos.presentation.main.model.toTrackRootUi
import com.anadolstudio.chronos.presentation.track.TrackNavigationArgs
import com.anadolstudio.chronos.util.TODAY
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.chronos.util.plusDay
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.common.NightModeRepository
import com.anadolstudio.domain.repository.common.PreferenceRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import com.anadolstudio.utils.util.rx.smartSubscribe
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import ru.cleverpumpkin.calendar.CalendarView
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

    companion object {
        private const val STOP_WATCHER_INTERVAL = 1L
        const val CATEGORIES_REQUEST_KEY = "1_000_001"
        const val CALENDAR_REQUEST_KEY = "1_000_111"
        const val TRACK_CHANGED_REQUEST_KEY = "1_000_121"
        const val EDIT_REQUEST_KEY = "1_000_011"
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

    override fun onTimeTrackChanged() = loadAllData()

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
        // TODO в UseCase
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

    override fun onCalendarClicked() = navigateTo(
            id = R.id.action_mainFragment_to_calendarBottom,
            args = resources.navigateArg(
                    CalendarNavigationArgs(
                            requestKey = CALENDAR_REQUEST_KEY,
                            fromDate = state.trackState.currentDate,
                            mode = CalendarView.SelectionMode.SINGLE
                    )
            )
    )

    override fun onAddClicked() = navigateTo(
            id = R.id.action_mainFragment_to_trackBottom,
            args = resources.navigateArg(
                    TrackNavigationArgs(
                            mainCategories = state.categoryState.mainCategoryList,
                            selectedDateTime = state.trackState.currentDate
                    )
            )
    )

    override fun onDiagramClicked() = navigateTo(R.id.action_mainFragment_to_statisticFragment)

    override fun onStopWatcherClicked() = navigateTo(R.id.action_mainFragment_to_stopWatcherFragment)

    override fun onEditItemsClicked() = navigateTo(
            id = R.id.action_mainFragment_to_categoriesBottom,
            args = resources.navigateArg(
                    CategoryNavigationArgs(
                            requestKey = CATEGORIES_REQUEST_KEY,
                            categoryList = state.categoryState.categoryList
                    )
            )
    )

    override fun onTrackClicked(trackRootUi: TrackRootUi) = navigateTo(
            id = R.id.action_mainFragment_to_trackDetailBottom,
            args = resources.navigateArg(
                    TrackDetailNavigationArgs(
                            requestKey = TRACK_CHANGED_REQUEST_KEY,
                            trackRootUi = trackRootUi,
                            currentDate = state.trackState.currentDate,
                            mainCategories = state.categoryState.mainCategoryList
                    )
            )
    )

    override fun onDateSelected(dateTime: Long) = changeCurrentDate(DateTime(dateTime).withTimeAtStartOfDay())

    override fun onPreviousDateSelected() = changeCurrentDate(state.trackState.currentDate.minusDay())

    override fun onNextDateSelected() = changeCurrentDate(state.trackState.currentDate.plusDay())

    private fun changeCurrentDate(currentDate: DateTime) {
        if (currentDate.isAfter(TODAY)) return

        preferenceRepository.lastSelectedDate = currentDate
        updateState { copy(trackState = trackState.copy(currentDate = currentDate)) }
        loadAllData()
    }

    override fun onStopWatcherToggleClicked() = stopWatcherDelegate.onStopWatcherToggleClicked()

    override fun onChangeNightModeClicked() = nightModeRepository.toggleNightMode()

    override fun onCategoriesSelected(categoryUi: CategoryUi) = navigateTo(
            id = R.id.action_mainFragment_to_editBottom,
            args = resources.navigateArg(
                    EditCategoryNavigationArgs(
                            requestKey = EDIT_REQUEST_KEY,
                            categoryList = state.categoryState.categoryList,
                            selectedCategory = categoryUi
                    )
            )
    )

}
