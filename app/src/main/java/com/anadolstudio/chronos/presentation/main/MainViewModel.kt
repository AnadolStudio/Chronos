package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel

class MainViewModel : BaseContentViewModel<MainState>(
    initState = MainState()
), MainController {

    override fun onCalendarClicked() = showTodo()

    override fun onAddClicked() = showTodo()

    override fun onChartClicked() = showTodo()

    override fun onStopWatcherClicked() = showTodo()

    override fun onEditItemsClicked() = showTodo()

    override fun onBackClicked() = Unit
}