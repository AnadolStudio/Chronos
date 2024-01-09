package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent
import org.joda.time.DateTime

sealed class MainEvents : SingleCustomEvent() {

    class ShowCalendar(
            val currentDateTime: DateTime,
            val maxDateTime: DateTime = DateTime.now()
    ) : MainEvents()
}
