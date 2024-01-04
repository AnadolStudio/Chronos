package com.anadolstudio.chronos.presentation.categories

import android.os.Parcelable
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryNavigationArgs(
        val categoryList: List<CategoryUi>
) : Parcelable
