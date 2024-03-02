package com.anadolstudio.chronos.presentation.edit.category

import com.anadolstudio.ui.viewmodel.livedata.SingleCustomEvent

sealed class EditCategoryBottomEvents : SingleCustomEvent() {

    object Result : EditCategoryBottomEvents()

    data class ShowConfirmRemoveAlert(val name:String) : EditCategoryBottomEvents()

}
