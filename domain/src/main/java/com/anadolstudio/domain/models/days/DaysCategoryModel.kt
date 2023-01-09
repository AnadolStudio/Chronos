package com.anadolstudio.domain.models.days

import android.graphics.Color
import java.util.UUID

object DaysCategoryModel {

    data class Category(
            val uuid: UUID,
            val title: String,
            val time: String,
            val color: Int,
            val subcategoriesList: List<Subcategory> = emptyList()
    )

    data class Subcategory(
            val uuid: UUID,
            val title: String,
            val time: String,
            val objectsList: List<SubcategoryObject> = emptyList()
    )

    data class SubcategoryObject(
            val uuid: UUID,
            val title: String,
            val time: String
    )

    // TODO temp
    val exampleCategory = Category(
            uuid = UUID.randomUUID(),
            title = UUID.randomUUID().toString(),
            time = "9ч 15м",
            color = Color.CYAN,
            subcategoriesList = listOf(
                    Subcategory(
                            uuid = UUID.randomUUID(),
                            title = UUID.randomUUID().toString(),
                            time = "15м"
                    ),
                    Subcategory(
                            uuid = UUID.randomUUID(),
                            title = UUID.randomUUID().toString(),
                            time = "9ч",
                            objectsList = listOf(
                                    SubcategoryObject(
                                            uuid = UUID.randomUUID(),
                                            title = UUID.randomUUID().toString(),
                                            time = "4ч"
                                    ),
                                    SubcategoryObject(
                                            uuid = UUID.randomUUID(),
                                            title = UUID.randomUUID().toString(),
                                            time = "5ч"
                                    )
                            )
                    ),
            )
    )
}


