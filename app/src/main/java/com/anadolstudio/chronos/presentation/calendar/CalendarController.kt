package com.anadolstudio.chronos.presentation.calendar

import com.anadolstudio.ui.viewmodel.BaseController
import ru.cleverpumpkin.calendar.CalendarDate

interface CalendarController : BaseController {
    fun onDatesSelected(selectedDates: List<CalendarDate>)
    fun onApplyClicked()
}
