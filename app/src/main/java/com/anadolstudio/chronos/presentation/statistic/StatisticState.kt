package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.chronos.util.TODAY
import com.anadolstudio.chronos.util.getMinutesInPeriod
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

data class StatisticState(
        val fromDate: DateTime = TODAY.minusDays(getStartDateDelta()),
        val toDate: DateTime = TODAY,
        val trackState: TrackState = TrackState(),
        val categoryState: MainCategoryState = MainCategoryState(),
        val isLoading: Boolean = true,
) {
    private companion object {
        fun getStartDateDelta(): Int = when {
            TODAY.dayOfMonth == 1 && TODAY.dayOfWeek == DateTimeConstants.MONDAY -> TODAY.minusDay().dayOfMonth
            TODAY.dayOfWeek == DateTimeConstants.MONDAY -> TODAY.dayOfMonth - 1
            else -> TODAY.dayOfWeek - 1
        }
    }

    val totalMinutesFromPeriod: Long = getMinutesInPeriod(fromDate, toDate, includeLastDay = true)
}

data class MainCategoryState(
        val mainCategoryList: List<MainCategoryDomain> = emptyList(),
) {
    val categoryList: List<CategoryUi> = mainCategoryList.toCategoryUi()
}

data class TrackState(
        val trackRootList: List<TrackRootUi> = emptyList()
) {
    val notEmptyTrackRootList: List<TrackRootUi> = trackRootList.filter { it.notEmptyChildren.isNotEmpty() }
}
