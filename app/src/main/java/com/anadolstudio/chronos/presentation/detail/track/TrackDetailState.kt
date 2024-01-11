package com.anadolstudio.chronos.presentation.detail.track

import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import org.joda.time.DateTime

data class TrackDetailState(
        val trackRootUi: TrackRootUi,
        val currentDate: DateTime,
        val mainCategories: List<MainCategoryDomain>,
)
