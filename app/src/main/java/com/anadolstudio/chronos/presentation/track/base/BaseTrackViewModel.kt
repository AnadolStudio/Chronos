package com.anadolstudio.chronos.presentation.track.base

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.utils.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import com.anadolstudio.domain.repository.common.ResourceRepository
import io.reactivex.Completable
import org.joda.time.DateTime
import kotlin.math.max

abstract class BaseTrackViewModel<State : BaseTrackState>(
        protected val resources: ResourceRepository,
        protected val chronosRepository: ChronosRepository,
        initState: State
) : BaseContentViewModel<State>(initState), BaseTrackController {

    companion object {
        private const val LAST_TRACKS_LIMIT = 30
        const val CATEGORIES_REQUEST_KEY = "1_000_002"
    }

    init {
        loadLastTracks()
    }

    private fun loadLastTracks() = chronosRepository.getLastTrackList(LAST_TRACKS_LIMIT)
            .smartSubscribe(
                    onSuccess = { trackList ->
                        val categories = trackList
                                .mapNotNull { state.categoryState.idCategoryMap[it.subcategoryId] }
                                .filter { !it.hasChild }

                        onLoadLastTracks(categories)
                    },
                    onError = this::showError
            )
            .disposeOnCleared()

    protected abstract fun onLoadLastTracks(categories: List<CategoryUi>)

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_trackBottom_to_categoriesBottom,
            args = resources.navigateArg(
                    CategoryNavigationArgs(
                            requestKey = CATEGORIES_REQUEST_KEY,
                            categoryList = state.categoryState.childCategoryList
                    )
            )
    )

    protected abstract fun updateLoading(isLoading: Boolean)

    protected fun trackCategory(
            it: TrackDomain,
            completable: Completable? = Completable.complete()
    ) = Completable.concatArray(createTrackCompletable(it), completable)
            .smartSubscribe(
                    onSubscribe = { updateLoading(true) },
                    onComplete = this::onSuccessTrack,
                    onError = this::showError,
                    onFinally = { updateLoading(false) }
            )
            .disposeOnCleared()

    protected abstract fun onSuccessTrack()

    private fun createTrackCompletable(currentCategory: TrackDomain): Completable = when (currentCategory.isNew) {
        true -> chronosRepository.insertTrack(currentCategory)
        false -> chronosRepository.updateTrack(currentCategory)
    }

    protected fun createNewTrack(currentCategory: CategoryUi, dateTime: DateTime): TrackDomain = TrackDomain(
            subcategoryId = currentCategory.id,
            minutes = state.time.totalMinutes,
            fromStopWatcher = state.fromStopWatcher,
            date = dateTime
    )

    override fun onHourPlusClicked() = updateTime(hours = state.hours + 1, minutes = state.minutes)

    override fun onHourMinusClicked() = updateTime(hours = max(state.hours - 1, 0), minutes = state.minutes)

    override fun onMinutesPlusClicked() = updateTime(hours = state.hours, minutes = state.minutes + 10)

    override fun onMinutesMinusClicked() = updateTime(hours = state.hours, minutes = max(state.minutes - 10, 0))

    override fun onRoundClicked() {
        val tens = (state.minutes / 10) * 10
        val minutes = when (state.minutes % 10) {
            in 0 until 3 -> 0
            in 3 until 8 -> 5
            else -> 10
        }

        updateTime(hours = state.hours, minutes = tens + minutes)
    }

    abstract fun updateTime(hours: Int, minutes: Int)
}
