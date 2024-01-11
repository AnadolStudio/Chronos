package com.anadolstudio.chronos.presentation.detail.track

import android.os.Parcelable
import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
class TrackDetailNavigationArgs(
        val trackRootUi: TrackRootUi,
        val mainCategories: List<MainCategoryDomain>,
        val currentDate: DateTime,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey), Parcelable
