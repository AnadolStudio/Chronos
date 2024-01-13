package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.main.model.toTrackRootUi
import com.anadolstudio.chronos.presentation.statistic.StatisticEvents.ShowFromDateCalendar
import com.anadolstudio.chronos.presentation.statistic.StatisticEvents.ShowToDateCalendar
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.chronos.util.plusDay
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<StatisticState>(
        initState = StatisticState()
), StatisticController {

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

}
