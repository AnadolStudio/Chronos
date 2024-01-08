package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import org.joda.time.DateTime

data class MainState(
        val stopWatcherData: StopWatcherData,
        val stopWatcherTime: Time? = null,
        val categoryState: MainCategoryState = MainCategoryState(),
        val trackState: TrackState = TrackState(),
        val currentDate: DateTime = DateTime.now(),
        val isLoading: Boolean = true,
)

data class MainCategoryState(
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
)

data class TrackState(val trackRootList: List<TrackRootUi> = emptyList()) {

    val notEmptyTrackRootList: List<TrackRootUi> = trackRootList.filter { it.time.totalMinutes > 0 }
}
