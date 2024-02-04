package com.anadolstudio.chronos.presentation.calendar

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class CalendarEvents : SingleCustomEvent() {

    class SingleResult(val dateTime: Long) : CalendarEvents()
    class MultiplyResult(val dateTimeList: List<Long>) : CalendarEvents()
}
