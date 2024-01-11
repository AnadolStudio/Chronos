package com.anadolstudio.chronos.base.viewmodel

import android.os.Bundle
import androidx.core.os.bundleOf
import com.anadolstudio.chronos.navigation.NavigateData
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreActionViewModel

abstract class BaseActionViewModel : CoreActionViewModel<NavigateData>(), BaseViewModelDelegate, BaseController {

    protected val baseViewModelDelegate: BaseViewModelDelegate = BaseViewModelDelegate.Delegate(_singleEvent)

    override fun showTodo(text: String?) = baseViewModelDelegate.showTodo(text)

    protected fun navigateTo(id: Int, args: Bundle = bundleOf()) = _navigationEvent.navigateTo(id, args)

    protected fun navigateUp() = _navigationEvent.navigateUp()

    override fun onBackClicked() = navigateUp()

}
