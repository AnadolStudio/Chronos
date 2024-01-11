package com.anadolstudio.chronos.base.bottom

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.di.SharedComponent
import com.anadolstudio.chronos.di.getSharedModule
import com.anadolstudio.chronos.navigation.NavigatableDelegate
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.Renderable
import com.anadolstudio.core.presentation.dialogs.bottom_sheet.CoreContentBottom
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.viewmodel.BaseController

abstract class BaseContentBottom<
        ViewState : Any,
        ViewModel : BaseContentViewModel<ViewState>,
        Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreContentBottom<ViewState, NavigateData, ViewModel, Controller>(layoutId), Renderable {

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())
    override val stateMap: MutableMap<String, Any?> = mutableMapOf()

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>

}
