package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData

data class MainState(
        val stopWatcherData: StopWatcherData,
        val stopWatcherTime: Time? = null,
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
        val isLoading: Boolean = true
)
