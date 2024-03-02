package com.anadolstudio.chronos.presentation.detail.track

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.edit.track.EditTrackNavigationArgs
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.presentation.main.model.toTrackRootUi
import com.anadolstudio.utils.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single

class TrackDetailViewModel @AssistedInject constructor(
        @Assisted args: TrackDetailNavigationArgs,
        private val chronosRepository: ChronosRepository,
        private val resources: ResourceRepository,
) : BaseContentViewModel<TrackDetailState>(
        initState = TrackDetailState(
                trackRootUi = args.trackRootUi,
                currentDate = args.currentDate,
                mainCategories = args.mainCategories
        )
), TrackDetailController {

    companion object {
        const val EDIT_TRACK_REQUEST_KEY = "1_000_014"
    }

    override fun onTrackChanged() {
        Single.zip(
                chronosRepository.getAllMainCategories(),
                chronosRepository.getTrackListByDate(state.currentDate)
                        .map { trackList -> trackList.associateBy { it.subcategoryId } }
        ) { mainCategoryList, trackMap ->
            val trackRootList = mainCategoryList.map { it.toTrackRootUi(trackMap) }

            trackRootList.filter { it.id == state.trackRootUi.mainCategoryId && it.time.totalMinutes > 0 }
        }.smartSubscribe(
                onSuccess = { trackRootList ->
                    when (val trackRoot = trackRootList.firstOrNull()) {
                        null -> navigateUpWithResult(TrackDetailBottomEvents.Result)
                        else -> updateState { copy(trackRootUi = trackRoot) }
                    }
                },
                onError = this::showError,
        ).disposeOnCleared()
    }

    override fun onTrackClicked(trackChildUi: TrackChildUi) {
        trackChildUi.id ?: return showMessage("trackChildUi.id must not be null")

        navigateTo(
                id = R.id.action_trackDetailBottom_to_editTrackBottom,
                args = resources.navigateArg(
                        EditTrackNavigationArgs(
                                mainCategories = state.mainCategories,
                                selectedDateTime = state.currentDate,
                                trackChildUi = trackChildUi,
                                requestKey = EDIT_TRACK_REQUEST_KEY
                        )
                )
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(args: TrackDetailNavigationArgs): TrackDetailViewModel
    }
}
