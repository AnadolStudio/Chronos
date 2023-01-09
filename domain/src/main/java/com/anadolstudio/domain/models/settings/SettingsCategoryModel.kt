package com.anadolstudio.domain.models.settings

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

object SettingsCategoryModel {

    @Parcelize
    data class Category(
            val uuid: UUID,
            val title: String,
            val color: Int,
            val subcategoriesList: List<Subcategory> = emptyList()
    ): Parcelable

    @Parcelize
    data class Subcategory(
            val uuid: UUID,
            val title: String,
            val objectsList: List<SubcategoryObject> = emptyList()
    ): Parcelable

    @Parcelize
    data class SubcategoryObject(
            val uuid: UUID,
            val title: String
    ): Parcelable

    fun createEmptyCategory(): Category = Category(
            uuid = UUID.randomUUID(),
            title = "",
            color = Color.WHITE,
            subcategoriesList = emptyList()
    )
}


