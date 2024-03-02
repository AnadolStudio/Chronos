package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.chronos.presentation.track.base.BaseTrackState
import com.anadolstudio.utils.util.data_time.Time
import org.joda.time.DateTime

data class TrackState(
        override val selectedDateTime: DateTime,
        override val categoryState: CategoryState,
        override val fromStopWatcher: Boolean,
        override val selectedCategoryUi: CategoryUi? = null,
        override val name: String = "",
        override val hours: Int = 0,
        override val minutes: Int = 0,
        override val isLoading: Boolean = false,
        override val lastTrackList: List<CategoryUi> = emptyList()
) : BaseTrackState {

    val applyButtonState: ApplyButtonState = ApplyButtonState(
            time = time,
            selectedCategoryUi = selectedCategoryUi,
            name = name,
            isNewName = categoryState.nameCategoryMap[name] == null
    )

}

data class ApplyButtonState(
        val isEnable: Boolean,
        val hasSelectedCategory: Boolean,
) {
    private companion object {
        const val MIN_TRACK_MINUTES = 10
    }

    constructor(
            selectedCategoryUi: CategoryUi?,
            name: String,
            time: Time,
            isNewName: Boolean,
    ) : this(
            isEnable = name.isNotBlank()
                    && (isNewName || (selectedCategoryUi?.isMainCategory == false && time.totalMinutes >= MIN_TRACK_MINUTES)),
            hasSelectedCategory = selectedCategoryUi != null,
    )
}
