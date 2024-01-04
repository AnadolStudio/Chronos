package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class TrackBottomEvents : SingleCustomEvent() {

    object Result : TrackBottomEvents()

}
