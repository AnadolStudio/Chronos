package com.anadolstudio.domain.repository.chronos.subcategory

import java.util.UUID

data class SubcategoryDomain(
    val id: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val mainCategoryId: UUID,
    val parentCategoryId: UUID,
    val subcategoryList: List<SubcategoryDomain>
)