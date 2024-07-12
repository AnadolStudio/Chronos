package com.anadolstudio.chronos.presentation.stopwatcher

import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import kotlinx.parcelize.Parcelize

@Parcelize
class StopWatcherArgs(
        override val requestKey: String,
) : BaseNavigationArgs(requestKey)
