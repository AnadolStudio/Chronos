package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.core.util.data_time.Time
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
            stopWatcherTime: Time? = null,
            categoryState: MainCategoryState = MainCategoryState(),
            currentDate: DateTime,
            isLoading: Boolean = true,
    ) : this(
            stopWatcherData = stopWatcherData,
            stopWatcherTime = stopWatcherTime,
            categoryState = categoryState,
            trackState = TrackState(currentDate = currentDate),
            isLoading = isLoading,
            isNightMode = isNightMode
    )
}

data class MainCategoryState(
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
)

data class TrackState(
        val currentDate: DateTime,
        val trackRootList: List<TrackRootUi> = emptyList()
) {
    val nextDateEnable = currentDate.isBefore(DateTime.now().withTimeAtStartOfDay())
    val notEmptyTrackRootList: List<TrackRootUi> = trackRootList.filter { it.notEmptyChildren.isNotEmpty() }
}
