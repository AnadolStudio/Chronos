package com.anadolstudio.chronos.presentation.calendar

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.joda.time.DateTime
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.MULTIPLE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.NONE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.RANGE
import ru.cleverpumpkin.calendar.CalendarView.SelectionMode.SINGLE

class CalendarViewModel @AssistedInject constructor(
        @Assisted args: CalendarNavigationArgs,
) : BaseContentViewModel<CalendarState>(
        initState = CalendarState(
                args = args,
        )
), CalendarController {

    override fun onDatesSelected(selectedDates: List<CalendarDate>) {
        val max = selectedDates.maxByOrNull { it.timeInMillis } ?: return
        val min = selectedDates.minByOrNull { it.timeInMillis } ?: return

        updateState { copy(fromDate = DateTime(min.timeInMillis), toDate = DateTime(max.timeInMillis)) }
    }

    override fun onApplyClicked() {
        val result = when (state.args.mode) {
            NONE, SINGLE -> CalendarEvents.SingleResult(state.fromDate.millis)
            MULTIPLE, RANGE -> CalendarEvents.MultiplyResult(state.selectedDates.map { it.millis })
        }
        navigateUpWithResult(result)
    }

    @AssistedFactory
    interface Factory {
        fun create(args: CalendarNavigationArgs): CalendarViewModel
    }
}
