package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.core.util.data_time.Time
import org.joda.time.DateTime

data class TrackState(
        val selectedDateTime: DateTime,
        val categoryState: CategoryState,
        val fromStopWatcher: Boolean,
        val selectedCategoryUi: CategoryUi? = null,
        val name: String = "",
        val hours: Int = 0,
        val minutes: Int = 0,
        val isLoading: Boolean = false,
        val lastTrackList: List<CategoryUi> = emptyList()
) {

    val time: Time get() = Time(hours = hours, minutes = minutes, seconds = 0)

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
            isEnable = name.isNotBlank() && (time.totalMinutes >= MIN_TRACK_MINUTES || isNewName),
            hasSelectedCategory = selectedCategoryUi != null,
    )
}
