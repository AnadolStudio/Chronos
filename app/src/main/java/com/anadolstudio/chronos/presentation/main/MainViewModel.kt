package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseContentViewModel<MainState>(
    initState = MainState()
), MainController {

    override fun onCalendarClicked() = showTodo()

    override fun onAddClicked() = showTodo()

    override fun onChartClicked() = showTodo()

    override fun onStopWatcherClicked() = showTodo()

    override fun onEditItemsClicked() = showTodo()

    override fun onBackClicked() = Unit
}