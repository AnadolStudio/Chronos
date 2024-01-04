package com.anadolstudio.chronos.presentation.stopwatcher

import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData

data class StopWatcherState(
        val stopWatcherData: StopWatcherData,
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
        val isLoading: Boolean = true
)
