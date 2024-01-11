package com.anadolstudio.chronos.presentation.edit.track

import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
class EditTrackNavigationArgs(
        val trackChildUi: TrackChildUi,
        val selectedDateTime: DateTime,
        val mainCategories: List<MainCategoryDomain>,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey)
