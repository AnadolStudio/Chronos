package com.anadolstudio.chronos.presentation.statistic

import com.anadolstudio.core.viewmodel.BaseController

interface StatisticController : BaseController {
    fun onFromDateSelected(year: Int, month: Int, dayOfMonth: Int)
    fun onToDateSelected(year: Int, month: Int, dayOfMonth: Int)
    fun onToDateClicked()
    fun onFromDateClicked()
}
