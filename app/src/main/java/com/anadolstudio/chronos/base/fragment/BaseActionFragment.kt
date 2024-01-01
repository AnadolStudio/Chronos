package com.anadolstudio.chronos.base.fragment

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.navigation.NavigatableDelegate
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.fragment.CoreActionFragment
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreActionViewModel

abstract class BaseActionFragment<ViewModel : CoreActionViewModel<NavigateData>, Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreActionFragment<Controller, NavigateData, ViewModel>(layoutId) {

    // TODO change delegate
    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())

    override var isStatusBarByNightMode: Boolean = true
}
