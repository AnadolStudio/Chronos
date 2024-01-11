package com.anadolstudio.chronos.presentation.detail.track

import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.core.viewmodel.BaseController

interface TrackDetailController : BaseController {
    fun onTrackChanged()
    fun onTrackClicked(trackChildUi: TrackChildUi)
}
