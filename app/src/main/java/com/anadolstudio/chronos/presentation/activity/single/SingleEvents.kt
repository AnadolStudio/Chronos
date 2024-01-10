package com.anadolstudio.chronos.presentation.activity.single

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class SingleEvents : SingleCustomEvent() {

    data class ChangeNightModeEvent(val mode: Int) : SingleEvents()
}
