package com.anadolstudio.chronos.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadolstudio.chronos.di.DI
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.onNextContent
import com.anadolstudio.core.rx_util.schedulersIoToMain
import com.anadolstudio.core.viewmodel.BaseViewModel
import com.anadolstudio.core.viewmodel.BaseViewState.Loading
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel.createEmptyCategory
import com.anadolstudio.domain.use_case.SettingUseCase
import java.util.UUID
import javax.inject.Inject

class SettingsViewModel(
        private val settingUseCase: SettingUseCase
) : BaseViewModel<SettingsContent>() {

    init {
        _state.onNext(Loading())

        settingUseCase.getAllCategoriesWithInnerData()
                .schedulersIoToMain()
                .subscribe { list ->
                    _state.onNextContent(SettingsContent(list +
                            createEmptyCategory().copy(subcategoriesList = listOf(SettingsCategoryModel.Subcategory(title = "1", uuid = UUID.randomUUID())))
                            + createEmptyCategory().copy(title = "Test")))
                }
                .disposeOnViewModelDestroy()

    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {

        @Inject
        lateinit var settingUseCase: SettingUseCase

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            DI.appComponent.inject(this)
            return SettingsViewModel(settingUseCase) as T
        }
    }

}
