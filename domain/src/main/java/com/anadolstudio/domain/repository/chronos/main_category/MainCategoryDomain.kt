package com.anadolstudio.domain.repository.chronos.main_category

import android.os.Parcelable
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class MainCategoryDomain(
        val id: Int = 0,
        val uuid: UUID = UUID.randomUUID(),
        val name: String,
        val color: Int,
        val subcategoryList: List<SubcategoryDomain> = emptyList()
) : Parcelable
