package com.anadolstudio.chronos.presentation.calendar

import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import com.anadolstudio.chronos.util.TODAY
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode

@Parcelize
class CalendarNavigationArgs(
        override val requestKey: String,
        val mode: SelectionMode,
        val fromDate: DateTime,
        val toDate: DateTime = fromDate,
        val minDate: DateTime? = null,
        val maxDate: DateTime = TODAY,
) : BaseNavigationArgs(requestKey) {

    @IgnoredOnParcel
    val selectedDates: List<CalendarDate>
        get() = when (mode) {
            SelectionMode.NONE, SelectionMode.SINGLE -> listOf(CalendarDate(fromDate.millis))
            SelectionMode.MULTIPLE, SelectionMode.RANGE -> listOf(CalendarDate(fromDate.millis), CalendarDate(toDate.millis))
        }

}
