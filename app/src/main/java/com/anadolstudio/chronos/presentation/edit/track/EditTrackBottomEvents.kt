package com.anadolstudio.chronos.presentation.edit.track

import com.anadolstudio.ui.viewmodel.livedata.SingleCustomEvent

sealed class EditTrackBottomEvents : SingleCustomEvent() {

    object Result : EditTrackBottomEvents()

}
