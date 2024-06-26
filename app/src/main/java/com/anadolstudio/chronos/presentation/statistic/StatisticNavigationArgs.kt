package com.anadolstudio.chronos.presentation.statistic

import android.os.Parcelable
import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
class StatisticNavigationArgs(
        val currentDate: DateTime?,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey), Parcelable
