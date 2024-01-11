package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryNavigationArgs(
        val categoryList: List<CategoryUi>,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey)
