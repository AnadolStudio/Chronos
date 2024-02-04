package com.anadolstudio.chronos.presentation.calendar

import org.joda.time.DateTime
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.MULTIPLE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.NONE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.RANGE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.SINGLE

data class CalendarState(
        val args: CalendarNavigationArgs,
        val fromDate: DateTime = args.fromDate,
        val toDate: DateTime = args.toDate,
) {

    private val hasChanges:Boolean get() = args.fromDate != fromDate || args.toDate != toDate

    val selectedDates: List<DateTime> get() = listOf(fromDate, toDate)

    val buttonEnable: Boolean
        get() = when (args.mode) {
            NONE, SINGLE -> hasChanges
            MULTIPLE, RANGE -> hasChanges && fromDate != toDate
        }
}
