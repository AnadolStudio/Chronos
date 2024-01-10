package com.anadolstudio.chronos.presentation.activity.single

import com.anadolstudio.chronos.base.viewmodel.BaseActionViewModel
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.common.NightModeRepository
import javax.inject.Inject

class SingleViewModel @Inject constructor(
        nightModeRepository: NightModeRepository,
) : BaseActionViewModel() {

    init {
        nightModeRepository.observeNightModeChanges()
                .smartSubscribe(
                        onSuccess = { showEvent(SingleEvents.ChangeNightModeEvent(it)) },
                        onError = this::showError
                )
                .disposeOnCleared()
    }

}
