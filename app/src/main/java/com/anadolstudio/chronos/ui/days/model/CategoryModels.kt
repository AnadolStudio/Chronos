package com.anadolstudio.chronos.ui.days.model

data class Category(
        val title: String,
        val time: String,
        val objectsList: List<Subcategory>
)

data class Subcategory(
        val title: String,
        val time: String,
        val objectsList: List<SubcategoryObject>
)

data class SubcategoryObject(
        val title: String,
        val time: String
)
