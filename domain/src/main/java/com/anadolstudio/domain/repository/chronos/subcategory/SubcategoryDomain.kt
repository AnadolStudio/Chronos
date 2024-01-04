package com.anadolstudio.domain.repository.chronos.subcategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class SubcategoryDomain(
        val id: Int = 0,
        val uuid: UUID = UUID.randomUUID(),
        val name: String,
        val mainCategoryId: UUID,
        val parentCategoryId: UUID,
        val subcategoryList: List<SubcategoryDomain>
) : Parcelable
