package com.anadolstudio.chronos.presentation.calendar

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomCalendarBinding
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.utils.util.extentions.setFragmentResult
import com.anadolstudio.utils.util.extentions.setSmartPadding
import ru.cleverpumpkin.calendar.CalendarDate

class CalendarBottom :
    BaseContentBottom<CalendarState, CalendarViewModel, CalendarController>(R.layout.bottom_calendar) {

    private val binding by viewBinding { BottomCalendarBinding.bind(it) }
    private val args: CalendarBottomArgs by navArgs()

    override fun createViewModelLazy(): Lazy<CalendarViewModel> = assistedViewModel {
        getSharedComponent()
            .calendarViewModelFactory()
            .create(args.data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is CalendarEvents.SingleResult -> setFragmentResult(args.data.requestKey, event.dateTime)
        is CalendarEvents.MultiplyResult -> setFragmentResult(
            args.data.requestKey,
            event.dateTimeList.toLongArray()
        )

        else -> super.handleEvent(event)
    }

    override fun initView() {
        val navArgs = args.data
        binding.calendar.setupCalendar(
            initialDate = CalendarDate(navArgs.fromDate.millis),
            maxDate = CalendarDate(navArgs.maxDate.millis),
            minDate = navArgs.minDate?.millis?.let { CalendarDate(it) },
            selectionMode = navArgs.mode,
            selectedDates = args.data.selectedDates,
            showYearSelectionView = true
        )
        binding.calendar.onDateClickListener = {
            controller.onDatesSelected(binding.calendar.selectedDates)
        }
        binding.applyButton.setOnClickListener { controller.onApplyClicked() }

        binding.root.post {
            val recycler = binding.calendar.findViewById<RecyclerView>(ru.cleverpumpkin.calendar.R.id.calendar_recycler_view)
            recycler.setSmartPadding(bottom = binding.applyButton.height)
            recycler.clipToPadding = false
        }
    }

    override fun render(state: CalendarState) {
        binding.applyButton.isEnabled = state.buttonEnable
    }

}
