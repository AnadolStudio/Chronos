package com.anadolstudio.chronos.base.fragment

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.base.viewmodel.BaseActionViewModel
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.getSharedModule
import com.anadolstudio.chronos.navigation.NavigatableDelegate
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.ui.Eventable
import com.anadolstudio.ui.Navigatable
import com.anadolstudio.ui.fragment.CoreActionFragment
import com.anadolstudio.ui.viewmodel.BaseController

abstract class BaseActionFragment<ViewModel : BaseActionViewModel, Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreActionFragment<Controller, NavigateData, ViewModel>(layoutId) {

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())

    override var isStatusBarByNightMode: Boolean = true

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>
}
