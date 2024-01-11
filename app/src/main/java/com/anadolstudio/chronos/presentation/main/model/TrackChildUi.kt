package com.anadolstudio.chronos.presentation.main.model

import android.os.Parcelable
import com.anadolstudio.core.util.data_time.Time
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.UUID
import java.util.concurrent.TimeUnit

@Parcelize
data class TrackChildUi(
        val id: UUID?,
        val subcategoryId: UUID,
        val name: String,
        val children: List<TrackChildUi>,
        val time: Time = Time(children.sumOf { TimeUnit.MINUTES.toMillis(it.time.totalMinutes.toLong()) })
) : Parcelable {

    constructor(
            id: UUID?,
            subcategoryId: UUID,
            name: String,
            children: List<TrackChildUi>,
            minutes: Int?
    ) : this(
            id = id,
            subcategoryId = subcategoryId,
            name = name,
            children = children,
            time = minutes
                    ?.let { Time(TimeUnit.MINUTES.toMillis(minutes.toLong())) }
                    ?: Time(children.sumOf { TimeUnit.MINUTES.toMillis(it.time.totalMinutes.toLong()) })
    )

    @IgnoredOnParcel
    val notEmptyChildren: List<TrackChildUi> = children
            .filter { it.time.totalMinutes > 0 }
            .map {
                val children = it.notEmptyChildren.filter { children -> children.time.totalMinutes > 0 }
                it.copy(children = children)
            }
}
