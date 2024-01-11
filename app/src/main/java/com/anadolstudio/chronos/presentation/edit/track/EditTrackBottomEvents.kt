package com.anadolstudio.chronos.presentation.edit.track

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class EditTrackBottomEvents : SingleCustomEvent() {

    object Result : EditTrackBottomEvents()

}
