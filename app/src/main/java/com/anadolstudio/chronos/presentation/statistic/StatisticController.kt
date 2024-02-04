package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.core.viewmodel.BaseController

interface StatisticController : BaseController {
    fun onPeriodClicked()
    fun onPreviousSwiped()
    fun onNextSwiped()
    fun onPeriodSelected(dates: List<Long>)
}
