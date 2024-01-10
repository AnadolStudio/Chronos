package com.anadolstudio.chronos.presentation.edit.category

import android.os.Parcelable
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.BaseNavigationArgs
import kotlinx.parcelize.Parcelize

@Parcelize
class EditCategoryNavigationArgs(
        val selectedCategory: CategoryUi,
        val categoryList: List<CategoryUi>,
        override val requestKey: String,
) : BaseNavigationArgs(requestKey), Parcelable
