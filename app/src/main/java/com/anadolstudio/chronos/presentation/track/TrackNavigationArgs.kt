package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
class TrackNavigationArgs(
        val selectedDateTime: DateTime,
        val mainCategories: List<MainCategoryDomain>,
        val hours: Int = 0,
        val minutes: Int = 0,
        val fromStopWatcher: Boolean = false,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey)
