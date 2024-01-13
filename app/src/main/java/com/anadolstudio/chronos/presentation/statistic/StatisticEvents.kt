package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent
import org.joda.time.DateTime

sealed class StatisticEvents : SingleCustomEvent() {

    class ShowFromDateCalendar(
            val currentDateTime: DateTime,
            val maxDateTime: DateTime = DateTime.now().minusDay()
    ) : StatisticEvents()

    class ShowToDateCalendar(
            val currentDateTime: DateTime,
            val minDateTime: DateTime = DateTime.now()
    ) : StatisticEvents()
}
