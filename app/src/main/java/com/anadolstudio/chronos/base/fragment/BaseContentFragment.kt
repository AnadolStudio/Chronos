package com.anadolstudio.chronos.base.fragment

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.getSharedModule
import com.anadolstudio.chronos.navigation.NavigatableDelegate
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.Renderable
import com.anadolstudio.core.presentation.fragment.CoreContentFragment
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreContentViewModel

abstract class BaseContentFragment<
        ViewState : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>(
    @LayoutRes layoutId: Int
) : CoreContentFragment<ViewState, NavigateData, ViewModel, Controller>(layoutId), Renderable {

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this) // TODO change delegate
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())
    override val stateMap: MutableMap<String, Any?> = mutableMapOf()

    override var isStatusBarByNightMode: Boolean = true

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>

}
