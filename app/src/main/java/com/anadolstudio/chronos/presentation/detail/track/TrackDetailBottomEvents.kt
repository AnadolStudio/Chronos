package com.anadolstudio.chronos.presentation.detail.track

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class TrackDetailBottomEvents : SingleCustomEvent() {

    object Result : TrackDetailBottomEvents()

}
