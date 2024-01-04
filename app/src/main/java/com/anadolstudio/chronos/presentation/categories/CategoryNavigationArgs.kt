package com.anadolstudio.chronos.presentation.categories

import android.os.Parcelable
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryNavigationArgs(
        val mainCategories: List<MainCategoryDomain>
) : Parcelable
