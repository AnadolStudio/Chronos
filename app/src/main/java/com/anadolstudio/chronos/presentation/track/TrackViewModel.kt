package com.anadolstudio.chronos.presentation.track

import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
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
                categoryList = args.mainCategories.flatMap { it.toCategoryUi() },
                hours = args.hours,
                minutes = args.minutes,
                fromStopWatcher = args.fromStopWatcher
        )
), TrackController {

    private companion object {
        const val LAST_TRACKS_LIMIT = 10
    }

    init {
        loadLastTracks()
    }

    private fun loadLastTracks() = chronosRepository.getLastTrackList(LAST_TRACKS_LIMIT)
            .smartSubscribe(
                    onSuccess = { trackList ->
                        val categories = trackList.mapNotNull { state.idCategoryMap[it.subcategoryId] }
                        updateState { copy(lastTrackList = categories) }
                    },
                    onError = this::showError
            )
            .disposeOnCleared()

    override fun onMinutesChanged(minutes: String) = updateState { copy(minutes = minutes.toIntOrNull() ?: 0) }

    override fun onHoursChanged(hours: String) = updateState { copy(hours = hours.toIntOrNull() ?: 0) }

    override fun onNameChanged(name: String) = updateState { copy(name = name, selectedCategoryUi = state.nameCategoryMap[name]) }

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_trackBottom_to_categoriesBottom,
            args = bundleOf(
                    resources.getString(string.data) to CategoryNavigationArgs(state.categoryList)
            )
    )

    override fun onCategoriesSelected(categoryUi: CategoryUi?) = updateState { copy(selectedCategoryUi = categoryUi) }

    override fun onTrackClicked() {
        val currentCategory = state.selectedCategoryUi

        if (currentCategory != null) {
            loadTrackByToday(currentCategory)
        } else {
            showTodo("Тут будет навигация на создание категории")
        }
    }

    private fun loadTrackByToday(currentCategory: CategoryUi) = chronosRepository.getTrackListByDate(DateTime.now())
            .map { trackList ->
                val previousTrack = trackList.firstOrNull { it.subcategoryId == currentCategory.id }
                        ?: return@map createNewTrack(currentCategory)

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
                        showEvent(TrackBottomEvents.Result)
                        navigateUp()
                    },
                    onError = this::showError,
                    onFinally = { updateState { copy(isLoading = false) } }
            )
            .disposeOnCleared()

    private fun createTrackCompletable(currentCategory: TrackDomain): Completable = when (currentCategory.isNew) {
        true -> chronosRepository.insertTrack(currentCategory)
        false -> chronosRepository.updateTrack(currentCategory)
    }

    private fun createNewTrack(currentCategory: CategoryUi): TrackDomain = TrackDomain(
            subcategoryId = currentCategory.id,
            minutes = state.time.totalMinutes,
            fromStopWatcher = true
    )

    @AssistedFactory
    interface Factory {
        fun create(args: TrackNavigationArgs): TrackViewModel
    }
}
