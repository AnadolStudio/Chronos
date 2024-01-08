package com.anadolstudio.chronos.presentation.main.model

import android.os.Parcelable
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import kotlinx.parcelize.Parcelize
import java.util.UUID
import java.util.concurrent.TimeUnit

@Parcelize
data class TrackRootUi(
        val id: UUID,
        val name: String,
        val color: Int,
        val children: List<TrackChildUi>,
        val time: Time = Time(children.sumOf { TimeUnit.MINUTES.toMillis(it.time.totalMinutes.toLong()) })
) : Parcelable

fun MainCategoryDomain.toTrackRootUi(trackMap: Map<UUID, TrackDomain>): TrackRootUi = TrackRootUi(
        id = uuid,
        name = name,
        color = color,
        children = subcategoryList.map { it.toTrackChildUi(trackMap) },
)

fun SubcategoryDomain.toTrackChildUi(trackMap: Map<UUID, TrackDomain>): TrackChildUi {
    val child = subcategoryList.map { childCategory ->
        TrackChildUi(
                id = childCategory.uuid,
                name = childCategory.name,
                children = childCategory.subcategoryList.map { it.toTrackChildUi(trackMap) },
                minutes = trackMap[childCategory.uuid]?.minutes
        )
    }

    return TrackChildUi(
            id = uuid,
            name = name,
            children = child,
            minutes = trackMap[uuid]?.minutes
    )
}
