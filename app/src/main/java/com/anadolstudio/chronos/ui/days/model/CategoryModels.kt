package com.anadolstudio.chronos.ui.days.model

import android.graphics.Color

data class Category(
        val title: String,
        val time: String,
        val color: Int,
        val subcategoriesList: List<Subcategory> = emptyList()
)

data class Subcategory(
        val title: String,
        val time: String,
        val objectsList: List<SubcategoryObject> = emptyList()
)

data class SubcategoryObject(
        val title: String,
        val time: String
)

// TODO temp
val exampleCategory = Category(
        title = "Сон",
        time = "9ч 15м",
        color = Color.CYAN,
        subcategoriesList = listOf(
                Subcategory(title = "Отдых", time = "15м"),
                Subcategory(
                        title = "Cон",
                        time = "9ч",
                        objectsList = listOf(
                                SubcategoryObject(title = "Дневной сон", time = "4ч"),
                                SubcategoryObject(title = "Ночной сон", time = "5ч")
                        )
                ),
        )
)

