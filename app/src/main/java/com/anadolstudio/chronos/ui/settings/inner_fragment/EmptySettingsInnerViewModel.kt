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
import com.anadolstudio.core.rx_util.schedulersIoToMain
import com.anadolstudio.core.rx_util.smartSubscribe
import com.anadolstudio.core.viewmodel.BaseViewModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.use_case.SettingUseCase
import javax.inject.Inject

class EmptySettingsInnerViewModel(
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

    fun addCategory() {
        val content = getContentOrNull() ?: return

        if (content.category.color == Color.WHITE) {
            _singleEvent.onNext(SingleMessageToast.Short(context.getString(R.string.message_empty_color)))
            return
        }

        if (content.category.title.isEmpty()) {
            _singleEvent.onNext(SingleMessageToast.Short(context.getString(R.string.message_empty_name)))
            return
        }

        settingUseCase.addCategoryWithInnerData(content.category)
                .schedulersIoToMain()
                .smartSubscribe(
                        onError = { it -> it.printStackTrace()},
                        onComplete = { _singleEvent.onNext(SingleMessageToast.Short("Done")) }
                )
                .disposeOnViewModelDestroy()
    }

    fun changeColor(color: Int) = updateContent {
        copy(category = category.copy(color = color))
    }

    fun changeName(name: String) = updateContent {
        copy(category = category.copy(title = name))
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
            return EmptySettingsInnerViewModel(context, category, settingUseCase) as T
        }
    }

}
