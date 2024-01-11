package com.anadolstudio.chronos.presentation.detail.track

import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import org.joda.time.DateTime

data class TrackDetailState(
        val trackRootUi: TrackRootUi,
        val currentDate: DateTime,
)
