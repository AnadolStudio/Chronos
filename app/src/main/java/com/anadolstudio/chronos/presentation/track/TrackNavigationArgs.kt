package com.anadolstudio.chronos.presentation.track

import android.os.Parcelable
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import kotlinx.parcelize.Parcelize

@Parcelize
class TrackNavigationArgs(
        val mainCategories: List<MainCategoryDomain>,
        val hours: Int = 0,
        val minutes: Int = 0,
        val fromStopWatcher: Boolean = false
) : Parcelable
