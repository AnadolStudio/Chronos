package com.anadolstudio.chronos.ui.days

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadolstudio.chronos.di.DI
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.viewmodel.BaseViewModel
import com.anadolstudio.core.viewmodel.BaseViewState.Loading
import com.anadolstudio.domain.use_case.SettingUseCase
import javax.inject.Inject

class DaysViewModel() : BaseViewModel<Unit>() {



    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DaysViewModel() as T
        }
    }

}
