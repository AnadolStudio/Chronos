package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseContentViewModel<MainState>(
    initState = MainState()
), MainController {

    override fun onCalendarClicked() = showTodo()

    override fun onAddClicked() = showTodo()

    override fun onChartClicked() = showTodo()

    override fun onStopWatcherClicked() = navigateTo(R.id.action_mainFragment_to_stopWatcherFragment)

    override fun onEditItemsClicked() = showTodo()

}