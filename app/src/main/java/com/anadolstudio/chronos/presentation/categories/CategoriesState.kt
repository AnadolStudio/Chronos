package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi

data class CategoriesState(
        val categoryMap: Map<String, List<CategoryUi>>
)
