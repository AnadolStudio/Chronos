package com.anadolstudio.chronos.presentation.track.base

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.core.util.data_time.Time
import org.joda.time.DateTime

interface BaseTrackState {
    val selectedDateTime: DateTime
    val categoryState: CategoryState
    val fromStopWatcher: Boolean
    val selectedCategoryUi: CategoryUi?
    val name: String
    val hours: Int
    val minutes: Int
    val isLoading: Boolean
    val lastTrackList: List<CategoryUi>

    val time: Time get() = Time(hours = hours, minutes = minutes, seconds = 0)

}
