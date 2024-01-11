package com.anadolstudio.chronos.base

import androidx.annotation.IdRes
import androidx.navigation.findNavController
import com.anadolstudio.chronos.base.viewmodel.BaseActionViewModel
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.getSharedModule
import com.anadolstudio.chronos.navigation.NavigatableDelegate
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.activity.CoreActionActivity
import com.anadolstudio.core.viewmodel.BaseController

abstract class BaseActionActivity<
        ViewModel : BaseActionViewModel,
        Controller : BaseController>(
        @IdRes val navigateContainerId: Int,
) : CoreActionActivity<Controller, NavigateData, ViewModel>() {

    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController(navigateContainerId))

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>
}
