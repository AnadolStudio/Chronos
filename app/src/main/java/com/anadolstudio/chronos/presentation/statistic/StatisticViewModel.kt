package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.main.model.toTrackRootUi
import com.anadolstudio.chronos.presentation.statistic.StatisticEvents.ShowFromDateCalendar
import com.anadolstudio.chronos.presentation.statistic.StatisticEvents.ShowToDateCalendar
import com.anadolstudio.chronos.util.endMonth
import com.anadolstudio.chronos.util.endWeek
import com.anadolstudio.chronos.util.getMinutesInPeriod
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.chronos.util.minusMonth
import com.anadolstudio.chronos.util.minusWeek
import com.anadolstudio.chronos.util.orToday
import com.anadolstudio.chronos.util.plusDay
import com.anadolstudio.chronos.util.plusMonth
import com.anadolstudio.chronos.util.plusWeek
import com.anadolstudio.chronos.util.startMonth
import com.anadolstudio.chronos.util.startWeek
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<StatisticState>(
        initState = StatisticState()
), StatisticController {

    private companion object {
        val DAYS_OF_WEEK_MINUTES = TimeUnit.DAYS.toMinutes(7L)
    }

    init {
        loadAllData()
    }

    private fun loadAllData() {
        // TODO в UseCase
        Single.zip(
                chronosRepository.getAllMainCategories(),
                chronosRepository.getTrackListByPeriod(state.fromDate, state.toDate)
                        .map { trackList ->
                            trackList
                                    .groupBy { it.subcategoryId }
                                    .mapValues { (_, list) ->
                                        val sum = list.sumOf { it.minutes }
                                        list.first().copy(minutes = sum)
                                    }
                        }
        ) { mainCategoryList, trackMap ->
            val trackRootList = mainCategoryList.map { it.toTrackRootUi(trackMap) }

            return@zip mainCategoryList to trackRootList
        }.smartSubscribe(
                onSubscribe = { updateState { copy(isLoading = true) } },
                onSuccess = { (mainCategoryList, trackRootList) ->
                    updateState {
                        copy(
                                categoryState = categoryState.copy(mainCategoryList = mainCategoryList),
                                trackState = trackState.copy(trackRootList = trackRootList)
                        )
                    }
                },
                onError = this::showError,
                onFinally = { updateState { copy(isLoading = false) } }
        ).disposeOnCleared()
    }

    override fun onFromDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        updateState { copy(fromDate = DateTime(year, month, dayOfMonth, 0, 0)) }
        loadAllData()
    }

    override fun onToDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        updateState { copy(toDate = DateTime(year, month, dayOfMonth, 0, 0)) }
        loadAllData()
    }

    override fun onToDateClicked() = showEvent(
            ShowToDateCalendar(currentDateTime = state.toDate, minDateTime = state.fromDate.plusDay())
    )

    override fun onFromDateClicked() = showEvent(
            ShowFromDateCalendar(currentDateTime = state.fromDate, maxDateTime = state.toDate.minusDay())
    )

    override fun onPreviousSwiped() {
        var (from, to) = when (getCurrentPeriodType()) {
            Period.WEEK -> state.fromDate.minusWeek().startWeek to state.toDate.minusWeek().endWeek
            Period.MONTH -> state.fromDate.minusMonth().startMonth to state.toDate.minusMonth().endMonth
        }

        if (getCurrentPeriodType() == Period.MONTH && from.monthOfYear() != to.monthOfYear()) {
            from = from.plusMonth().startMonth
        }

        updatePeriod(from, to)
    }

    override fun onNextSwiped() {
        var (from, to) = when (getCurrentPeriodType()) {
            Period.WEEK -> state.fromDate.plusWeek().startWeek to state.toDate.plusWeek().endWeek.orToday
            Period.MONTH -> state.fromDate.plusMonth().startMonth to state.toDate.plusMonth().endMonth.orToday
        }

        if (getCurrentPeriodType() == Period.MONTH && from.monthOfYear() != to.monthOfYear()) {
            from = from.plusMonth().startMonth
        }

        if (from.isAfter(to)) return

        if (DAYS_OF_WEEK_MINUTES > getMinutesInPeriod(from, to, includeLastDay = true)) {
            val initState = StatisticState()
            updatePeriod(initState.fromDate, initState.toDate)

            return
        }

        updatePeriod(from, to)
    }

    private fun updatePeriod(fromDate: DateTime, toDate: DateTime) {
        if (state.fromDate.millis == fromDate.millis && state.toDate.millis == toDate.millis) return

        updateState { copy(fromDate = fromDate, toDate = toDate) }
        loadAllData()
    }

    private fun getCurrentPeriodType(): Period = when {
        DAYS_OF_WEEK_MINUTES >= state.totalMinutesFromPeriod -> Period.WEEK
        else -> Period.MONTH
    }

    private enum class Period { WEEK, MONTH }
}
