package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.track.base.BaseTrackController

interface TrackController : BaseTrackController {
    fun onCategoryCreated(categoryUi: CategoryUi)
}
