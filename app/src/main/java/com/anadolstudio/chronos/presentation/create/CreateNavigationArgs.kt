package com.anadolstudio.chronos.presentation.create

import android.os.Parcelable
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateNavigationArgs(
        val categoryList: List<CategoryUi>,
        val name: String
) : Parcelable
