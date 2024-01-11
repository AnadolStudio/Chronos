package com.anadolstudio.chronos.presentation.track

import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.chronos.presentation.create.CreateNavigationArgs
import com.anadolstudio.core.R.string
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Completable
import org.joda.time.DateTime

class TrackViewModel @AssistedInject constructor(
        @Assisted args: TrackNavigationArgs,
        private val resources: ResourceRepository,
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<TrackState>(
        initState = TrackState(
                categoryState = CategoryState(args.mainCategories),
                hours = args.hours,
                minutes = args.minutes,
                fromStopWatcher = args.fromStopWatcher,
                selectedDateTime = args.selectedDateTime
        )
), TrackController {

    companion object {
        private const val LAST_TRACKS_LIMIT = 10
        const val CATEGORIES_REQUEST_KEY = "1_000_002"
    }

    init {
        loadLastTracks()
    }

    private fun loadLastTracks() = chronosRepository.getLastTrackList(LAST_TRACKS_LIMIT)
            .smartSubscribe(
                    onSuccess = { trackList ->
                        val categories = trackList.mapNotNull {
                            state.categoryState.idCategoryMap[it.subcategoryId]
                        }
                        updateState { copy(lastTrackList = categories) }
                    },
                    onError = this::showError
            )
            .disposeOnCleared()

    override fun onMinutesChanged(minutes: String) = updateState {
        copy(minutes = minutes.toIntOrNull() ?: 0)
    }

    override fun onHoursChanged(hours: String) = updateState {
        copy(hours = hours.toIntOrNull() ?: 0)
    }

    override fun onNameChanged(name: String) = updateState {
        copy(name = name, selectedCategoryUi = state.categoryState.childNameCategoryMap[name])
    }

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_trackBottom_to_categoriesBottom,
            args = bundleOf(
                    resources.getString(string.data) to CategoryNavigationArgs(
                            requestKey = CATEGORIES_REQUEST_KEY,
                            categoryList = state.categoryState.childCategoryList
                    )
            )
    )

    override fun onCategoriesSelected(categoryUi: CategoryUi) = updateState {
        copy(
                selectedCategoryUi = categoryUi,
                name = categoryUi.name
        )
    }

    override fun onCategoryCreated(categoryUi: CategoryUi) {
        chronosRepository.getAllMainCategories()
                .smartSubscribe(
                        onSubscribe = { updateState { copy(isLoading = true) } },
                        onSuccess = { mainCategoryList ->
                            updateState {

                                val state = CategoryState(mainCategoryList)

                                copy(
                                        categoryState = state,
                                        selectedCategoryUi = state.idCategoryMap[categoryUi.id],
                                        name = categoryUi.name
                                )
                            }
                        },
                        onError = this::showError,
                        onFinally = { updateState { copy(isLoading = false) } }
                )
                .disposeOnCleared()

    }

    override fun onTrackClicked() {
        val currentCategory = state.selectedCategoryUi

        if (currentCategory != null) {
            loadTrackByDay(currentCategory, state.selectedDateTime)
        } else {
            navigateTo(
                    id = R.id.action_trackBottom_to_createBottom,
                    args = bundleOf(
                            resources.getString(string.data) to CreateNavigationArgs(
                                    categoryList = state.categoryState.categoryList,
                                    name = state.name
                            )
                    )
            )
        }
    }

    private fun loadTrackByDay(currentCategory: CategoryUi, dateTime: DateTime) = chronosRepository.getTrackListByDate(dateTime)
            .map { trackList ->
                val previousTrack = trackList.firstOrNull { it.subcategoryId == currentCategory.id }
                        ?: return@map createNewTrack(currentCategory, dateTime)

                previousTrack.copy(
                        minutes = previousTrack.minutes + state.time.totalMinutes,
                        isNew = false
                )

            }
            .smartSubscribe(
                    onSuccess = this::trackCategory,
                    onError = this::showError
            )
            .disposeOnCleared()

    private fun trackCategory(it: TrackDomain) = createTrackCompletable(it)
            .smartSubscribe(
                    onComplete = {
                        navigateUpWithResult(TrackBottomEvents.Result)
                    },
                    onError = this::showError,
                    onFinally = { updateState { copy(isLoading = false) } }
            )
            .disposeOnCleared()

    private fun createTrackCompletable(currentCategory: TrackDomain): Completable = when (currentCategory.isNew) {
        true -> chronosRepository.insertTrack(currentCategory)
        false -> chronosRepository.updateTrack(currentCategory)
    }

    private fun createNewTrack(currentCategory: CategoryUi, dateTime: DateTime): TrackDomain = TrackDomain(
            subcategoryId = currentCategory.id,
            minutes = state.time.totalMinutes,
            fromStopWatcher = state.fromStopWatcher,
            date = dateTime
    )

    @AssistedFactory
    interface Factory {
        fun create(args: TrackNavigationArgs): TrackViewModel
    }
}
