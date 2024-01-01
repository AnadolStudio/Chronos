package com.anadolstudio.chronos.base.viewmodel

import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.viewmodel.CoreContentViewModel

abstract class BaseContentViewModel<State : Any>(
        initState: State
) : CoreContentViewModel<State, NavigateData>(initState), BaseViewModelDelegate {

    protected val baseViewModelDelegate: BaseViewModelDelegate = BaseViewModelDelegate.Delegate(_singleEvent)

    override fun showTodo() = baseViewModelDelegate.showTodo()

}
