package com.anadolstudio.chronos.base

import androidx.annotation.LayoutRes
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.viewmodel.BaseViewModel

abstract class BaseFragment<ViewState, ViewModel:BaseViewModel<ViewState>>(
        @LayoutRes layoutId: Int
) : CoreBaseFragment<ViewState, ViewModel>(layoutId) {

    override fun handleEvent(event: SingleEvent) = when (event) {
        is SingleNavigationEvent -> Unit
        else -> super.handleEvent(event)
    }

    // TODO
    open fun handleNavigationEvent(event: SingleNavigationEvent) = Unit
}
