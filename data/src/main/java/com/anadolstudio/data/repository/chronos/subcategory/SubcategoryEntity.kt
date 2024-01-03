package com.anadolstudio.data.repository.chronos.subcategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryEntity
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import java.util.UUID

@Entity(tableName = SubcategoryEntity.SUBCATEGORY_TABLE)
data class SubcategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: UUID,
    @ColumnInfo(name = "main_category_id") val mainCategoryId: UUID,
    @ColumnInfo(name = "parent_category_id") val parentCategoryId: UUID,
    @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        const val SUBCATEGORY_TABLE = "subcategory_table"
    }

    val isRoot: Boolean get() = mainCategoryId == parentCategoryId
}

fun SubcategoryDomain.toEntity(): SubcategoryEntity = SubcategoryEntity(
    id = id,
    uuid = uuid,
    mainCategoryId = mainCategoryId,
    parentCategoryId = parentCategoryId,
    name = name
)

fun SubcategoryEntity.toDomain(
    innerSubcategories: List<SubcategoryDomain> = emptyList()
): SubcategoryDomain = SubcategoryDomain(
    id = id,
    uuid = uuid,
    mainCategoryId = mainCategoryId,
    parentCategoryId = parentCategoryId,
    name = name,
    subcategoryList = innerSubcategories
)
