package com.anadolstudio.chronos.base.viewmodel

import android.os.Bundle
import androidx.core.os.bundleOf
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.ui.viewmodel.BaseController
import com.anadolstudio.ui.viewmodel.CoreContentViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleCustomEvent

abstract class BaseContentViewModel<State : Any>(
        initState: State
) : CoreContentViewModel<State, NavigateData>(initState), BaseViewModelDelegate, BaseController {

    protected val baseViewModelDelegate: BaseViewModelDelegate = BaseViewModelDelegate.Delegate(_singleEvent)

    override fun showTodo(text: String?) = baseViewModelDelegate.showTodo(text)

    protected fun navigateTo(id: Int, args: Bundle = bundleOf()) = _navigationEvent.navigateTo(id, args)

    protected fun navigateUp() = _navigationEvent.navigateUp()

    protected fun navigateUpWithResult(event: SingleCustomEvent) {
        _navigationEvent.navigateUp()
        showEvent(event)
    }

    override fun onBackClicked() = navigateUp()

}
