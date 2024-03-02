package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.chronos.util.TODAY
import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import org.joda.time.DateTime

data class MainState(
        val stopWatcherData: StopWatcherData,
        val trackState: TrackState,
        val isNightMode: Boolean,
        val stopWatcherTime: Time? = null,
        val categoryState: MainCategoryState = MainCategoryState(),
        val isLoading: Boolean = true,
) {
    constructor(
            stopWatcherData: StopWatcherData,
            isNightMode: Boolean,
            stopWatcherTime: Time?,
            currentDate: DateTime,
    ) : this(
            stopWatcherData = stopWatcherData,
            stopWatcherTime = stopWatcherTime,
            categoryState = MainCategoryState(),
            trackState = TrackState(currentDate = currentDate),
            isLoading = true,
            isNightMode = isNightMode
    )
}

data class MainCategoryState(
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
){
    val categoryList: List<CategoryUi> = mainCategoryList.toCategoryUi()
}

data class TrackState(
        val currentDate: DateTime,
        val trackRootList: List<TrackRootUi> = emptyList()
) {
    val nextDateEnable = currentDate.isBefore(TODAY)
    val notEmptyTrackRootList: List<TrackRootUi> = trackRootList.filter { it.notEmptyChildren.isNotEmpty() }
}
