package com.anadolstudio.chronos.base

import androidx.annotation.LayoutRes
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.livedata.SingleEvent

abstract class BaseFragment<ViewState>(@LayoutRes layoutId: Int) : CoreBaseFragment<ViewState>(layoutId) {

    override fun handleEvent(event: SingleEvent) = when (event) {
        is SingleNavigationEvent -> Unit
        else -> super.handleEvent(event)
    }

    // TODO
    open fun handleNavigationEvent(event: SingleNavigationEvent) = Unit
}
