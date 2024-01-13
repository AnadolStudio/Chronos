package com.anadolstudio.chronos.presentation.edit.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.presentation.track.base.BaseTrackState
import org.joda.time.DateTime

data class EditTrackState(
        val trackChildUi: TrackChildUi,
        override val selectedDateTime: DateTime,
        override val categoryState: CategoryState,
        override val fromStopWatcher: Boolean = false,
        override val selectedCategoryUi: CategoryUi? = categoryState.nameCategoryMap[trackChildUi.name],
        override val name: String = trackChildUi.name,
        override val hours: Int = 0,
        override val minutes: Int = 0,
        override val isLoading: Boolean = false,
        override val lastTrackList: List<CategoryUi> = emptyList()
) : BaseTrackState {

    private companion object {
        const val MIN_TRACK_MINUTES = 10
    }

    val buttonEnable: Boolean = (time.totalMinutes >= MIN_TRACK_MINUTES || time.totalMinutes == 0)
            && (trackChildUi.time.totalMinutes != time.totalMinutes
            || (name != trackChildUi.name && categoryState.nameCategoryMap[name] != null))

    val buttonState: ButtonState
        get() = when {
            time.totalMinutes == 0 -> ButtonState.REMOVE
            selectedCategoryUi != null && selectedCategoryUi.id == trackChildUi.subcategoryId -> ButtonState.UPDATE
            else -> ButtonState.TRANSFER
        }

}

enum class ButtonState {
    REMOVE,
    TRANSFER,
    UPDATE
}
