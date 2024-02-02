package com.anadolstudio.chronos.presentation.statistic

import android.view.GestureDetector
import androidx.fragment.app.viewModels
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentStatisticBinding
import com.anadolstudio.chronos.presentation.main.TrackItem
import com.anadolstudio.chronos.presentation.main.TrackStubItem
import com.anadolstudio.chronos.util.CalendarDialog
import com.anadolstudio.chronos.util.DateListener
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.core.groupie.BaseGroupAdapter
import com.anadolstudio.core.view.gesture.HorizontalMoveGesture
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.xwray.groupie.Section
import org.joda.time.DateTime

class StatisticFragment : BaseContentFragment<StatisticState, StatisticViewModel, StatisticController>(R.layout.fragment_statistic) {

    private companion object {
        const val RENDER_TRACK = "RENDER_TRACK"
    }

    private val binding by viewBinding { FragmentStatisticBinding.bind(it) }
    private val trackSection: Section = Section()
    private val diagramSection: Section = Section()
    private val horizontalMoveGestureDetector: GestureDetector by lazy {
        GestureDetector(
                context,
                HorizontalMoveGesture(
                        width = binding.recycler.width,
                        onSwipeLeft = controller::onNextSwiped,
                        onSwipeRight = controller::onPreviousSwiped
                )
        )
    }

    override fun createViewModelLazy() = viewModels<StatisticViewModel> { viewModelFactory }

    override fun initView() = with(binding) {
        binding.toolbar.setBackClickListener { controller.onBackClicked() }
        recycler.adapter = BaseGroupAdapter(diagramSection, trackSection)
        binding.recyclerContainer.addDispatchTouchListener { _, event ->
            horizontalMoveGestureDetector.onTouchEvent(event)
        }
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is StatisticEvents.ShowToDateCalendar -> showToCalendarPicker(event)
        is StatisticEvents.ShowFromDateCalendar -> showFromCalendarPicker(event)
        else -> super.handleEvent(event)
    }

    private fun showFromCalendarPicker(event: StatisticEvents.ShowFromDateCalendar) = with(event) {
        showCalendarPicker(currentDateTime, maxDateTime, null, controller::onFromDateSelected)
    }

    private fun showToCalendarPicker(event: StatisticEvents.ShowToDateCalendar) = with(event) {
        showCalendarPicker(currentDateTime, DateTime.now(), minDateTime, controller::onToDateSelected)
    }

    private fun showCalendarPicker(currentDate: DateTime, maxDate: DateTime, minDate: DateTime?, listener: DateListener) {
        CalendarDialog.show(
                context = requireContext(),
                currentDateTime = currentDate,
                minDate = minDate,
                maxDate = maxDate,
                showFromYear = false,
                dateListener = listener
        )
    }

    override fun render(state: StatisticState) = state.render(RENDER_TRACK) {
        val trackItems = trackState.notEmptyTrackRootList
                .map { TrackItem(trackRootUi = it) }
                .ifEmpty { listOf(TrackStubItem()) }

        trackSection.update(trackItems)

        val totalMinutes = trackState.notEmptyTrackRootList.sumOf { it.time.totalMinutes }

        val progressDataList = trackState.notEmptyTrackRootList.map {
            ProgressData(color = it.color, value = it.time.totalMinutes)
        }.toMutableList()

        if (totalMinutes < totalMinutesFromPeriod) {
            val other = ProgressData(
                    color = requireContext().getColor(R.color.disableBackground),
                    value = totalMinutesFromPeriod.toInt() - totalMinutes
            )

            progressDataList.add(other)
        }

        val diagramItem = DiagramItem(
                data = DiagramItem.Data(
                        hours = totalMinutes / 60F,
                        totalMinutes = totalMinutesFromPeriod,
                        fromDate = fromDate,
                        toDate = toDate,
                        progressDataList = progressDataList,
                        onFromDateClick = controller::onFromDateClicked,
                        onToDateClick = controller::onToDateClicked,
                ),
        )

        diagramSection.update(listOf(diagramItem))
    }
}
