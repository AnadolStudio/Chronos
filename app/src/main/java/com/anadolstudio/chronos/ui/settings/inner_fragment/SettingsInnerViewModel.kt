package com.anadolstudio.chronos.ui.settings.inner_fragment

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.di.DI
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.onNextContent
import com.anadolstudio.core.viewmodel.BaseViewModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.use_case.SettingUseCase
import javax.inject.Inject

class SettingsInnerViewModel(
        private val context: Context,
        category: SettingsCategoryModel.Category,
        private val settingUseCase: SettingUseCase
) : BaseViewModel<SettingsInnerContent>() {

    init {
        _state.onNextContent(
                SettingsInnerContent(
                        isEmpty = category.title.isEmpty(),
                        category = category
                )
        )
    }


    fun changeColor(color: Int) {

    }

    fun changeName(name: String) {

    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
            private val category: SettingsCategoryModel.Category
    ) : ViewModelProvider.NewInstanceFactory() {

        @Inject
        lateinit var context: Context

        @Inject
        lateinit var settingUseCase: SettingUseCase

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            DI.appComponent.inject(this)
            return SettingsInnerViewModel(context, category, settingUseCase) as T
        }
    }

}
