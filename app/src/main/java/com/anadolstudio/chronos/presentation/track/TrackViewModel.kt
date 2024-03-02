package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.chronos.presentation.create.CreateNavigationArgs
import com.anadolstudio.chronos.presentation.track.base.BaseTrackViewModel
import com.anadolstudio.utils.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.joda.time.DateTime

open class TrackViewModel @AssistedInject constructor(
        @Assisted args: TrackNavigationArgs,
        resources: ResourceRepository,
        chronosRepository: ChronosRepository,
) : BaseTrackViewModel<TrackState>(
        initState = TrackState(
                categoryState = CategoryState(args.mainCategories),
                hours = args.hours,
                minutes = args.minutes,
                fromStopWatcher = args.fromStopWatcher,
                selectedDateTime = args.selectedDateTime,
        ),
        resources = resources,
        chronosRepository = chronosRepository,
), TrackController {

    override fun onMinutesChanged(minutes: String) = updateState(forceUpdate = (minutes.toIntOrNull() ?: 0) == 0) {
        copy(minutes = minutes.toIntOrNull() ?: 0)
    }

    override fun onHoursChanged(hours: String) = updateState(forceUpdate = (hours.toIntOrNull() ?: 0) == 0) {
        copy(hours = hours.toIntOrNull() ?: 0)
    }

    override fun onNameChanged(name: String) = updateState {
        copy(name = name, selectedCategoryUi = state.categoryState.childNameCategoryMap[name])
    }

    override fun onCategoriesSelected(categoryUi: CategoryUi) = updateState {
        copy(selectedCategoryUi = categoryUi, name = categoryUi.name)
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

        showEvent(TrackBottomEvents.Result)
    }

    override fun onTrackClicked() {
        val currentCategory = state.selectedCategoryUi

        if (currentCategory != null) {
            loadTrackByDay(currentCategory, state.selectedDateTime)
        } else {
            navigateTo(
                    id = R.id.action_trackBottom_to_createBottom,
                    args = resources.navigateArg(
                            CreateNavigationArgs(
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
                    onSubscribe = { updateLoading(true) },
                    onSuccess = this::trackCategory,
                    onError = this::showError
            )
            .disposeOnCleared()

    override fun onSuccessTrack() = navigateUpWithResult(TrackBottomEvents.Result)

    override fun onLoadLastTracks(categories: List<CategoryUi>) = updateState { copy(lastTrackList = categories) }

    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    override fun updateTime(hours: Int, minutes: Int) = updateState { copy(hours = hours, minutes = minutes) }

    @AssistedFactory
    interface Factory {
        fun create(args: TrackNavigationArgs): TrackViewModel
    }
}
