package com.anadolstudio.chronos.presentation.edit.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.chronos.presentation.track.base.BaseTrackViewModel
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single

class EditTrackViewModel @AssistedInject constructor(
        @Assisted args: EditTrackNavigationArgs,
        resources: ResourceRepository,
        chronosRepository: ChronosRepository,
) : BaseTrackViewModel<EditTrackState>(
        resources = resources,
        chronosRepository = chronosRepository,
        initState = EditTrackState(
                trackChildUi = args.trackChildUi,
                categoryState = CategoryState(args.mainCategories),
                hours = args.trackChildUi.time.hours,
                minutes = args.trackChildUi.time.minutes,
                selectedDateTime = args.selectedDateTime,
        )
) {

    override fun onTrackClicked() = when (state.time.totalMinutes == 0) {
        true -> removeOldTrack()
        false -> changeTrack()
    }

    private fun changeTrack() {
        val category = state.selectedCategoryUi ?: return
        val timeChanged = state.trackChildUi.time.totalMinutes != state.time.totalMinutes

        when {
            !timeChanged && category.id != state.trackChildUi.subcategoryId -> transferTrack(category)
            category.id != state.trackChildUi.subcategoryId -> transferPartTrack(category)
            timeChanged -> updateTrack(category)
            else -> return
        }
    }

    private fun transferTrack(category: CategoryUi) {
        val id = state.trackChildUi.id ?: return showMessage("trackChildUi.id must not be null")

        provideLastTrackSingle(EditType.TRANSFER, category)
                .smartSubscribe(
                        onSubscribe = { updateLoading(true) },
                        onSuccess = { trackCategory(it, chronosRepository.deleteTrackById(id)) },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    private fun transferPartTrack(category: CategoryUi) {
        val id = state.trackChildUi.id ?: return showMessage("trackChildUi.id must not be null")

        provideLastTrackSingle(EditType.TRANSFER_PART, category)
                .smartSubscribe(
                        onSubscribe = { updateLoading(true) },
                        onSuccess = { trackDomain ->
                            val newTime = state.trackChildUi.time.totalMinutes - state.time.totalMinutes

                            val completable = if (newTime > 0) {
                                chronosRepository.updateTrackById(id, newTime)
                            } else {
                                chronosRepository.deleteTrackById(id)
                            }

                            trackCategory(trackDomain, completable)
                        },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    private fun updateTrack(category: CategoryUi) {
        provideLastTrackSingle(EditType.UPDATE, category)
                .smartSubscribe(
                        onSubscribe = { updateLoading(true) },
                        onSuccess = this::trackCategory,
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    private fun provideLastTrackSingle(type: EditType, category: CategoryUi): Single<TrackDomain> =
            chronosRepository.getTrackListByDate(state.selectedDateTime).map { trackList ->
                val previousTrack = trackList.firstOrNull { it.subcategoryId == category.id }

                if (previousTrack != null) {
                    return@map createUpdatedTrack(type, previousTrack).copy(isNew = false)

                } else {
                    when (type) {
                        EditType.UPDATE -> throw IllegalArgumentException("Not find previousTrack")
                        else -> return@map createNewTrack(category, state.selectedDateTime)
                    }
                }
            }

    private fun createUpdatedTrack(type: EditType, previousTrack: TrackDomain): TrackDomain = when (type) {
        EditType.TRANSFER -> previousTrack.copy(minutes = previousTrack.minutes + state.time.totalMinutes)
        EditType.TRANSFER_PART -> previousTrack.copy(minutes = previousTrack.minutes + state.time.totalMinutes)
        EditType.UPDATE -> previousTrack.copy(minutes = state.time.totalMinutes)
    }

    override fun onSuccessTrack() = navigateUpWithResult(EditTrackBottomEvents.Result)

    private fun removeOldTrack() {
        val id = state.trackChildUi.id ?: return showMessage("trackChildUi.id must not be null")

        chronosRepository.deleteTrackById(id)
                .smartSubscribe(
                        onComplete = { navigateUpWithResult(EditTrackBottomEvents.Result) },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

    override fun onNameChanged(name: String) {
        val category = when (name == state.trackChildUi.name) {
            true -> state.categoryState.nameCategoryMap[state.trackChildUi.name]
            false -> state.categoryState.childNameCategoryMap[name]
        }
        updateState { copy(name = name, selectedCategoryUi = category) }
    }

    override fun onMinutesChanged(minutes: String) = updateState(forceUpdate = (minutes.toIntOrNull() ?: 0) == 0) {
        copy(minutes = minutes.toIntOrNull() ?: 0)
    }

    override fun onHoursChanged(hours: String) = updateState(forceUpdate = (hours.toIntOrNull() ?: 0) == 0) {
        copy(hours = hours.toIntOrNull() ?: 0)
    }

    override fun onCategoriesSelected(categoryUi: CategoryUi) = updateState {
        copy(selectedCategoryUi = categoryUi, name = categoryUi.name)
    }

    override fun onLoadLastTracks(categories: List<CategoryUi>) = updateState { copy(lastTrackList = categories) }

    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    @AssistedFactory
    interface Factory {
        fun create(args: EditTrackNavigationArgs): EditTrackViewModel
    }

    private enum class EditType {
        TRANSFER,
        TRANSFER_PART,
        UPDATE,
    }
}
