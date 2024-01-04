package com.anadolstudio.chronos.presentation.track.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class TrackUi(
        val id: UUID,
        val name: String,
        val color: Int,
        val mainCategoryId: UUID,
        val parentCategoryId: UUID?,
        val parentName: String?,
) : Parcelable {

    constructor(id: UUID, name: String, color: Int) : this(
            id = id,
            name = name,
            color = color,
            mainCategoryId = id,
            parentCategoryId = null,
            parentName = null,
    )

    @IgnoredOnParcel
    val isMainCategory: Boolean = id == mainCategoryId

    @IgnoredOnParcel
    val isRootCategory: Boolean = mainCategoryId == parentCategoryId
}
