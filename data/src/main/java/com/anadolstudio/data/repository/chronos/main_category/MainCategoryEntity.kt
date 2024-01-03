package com.anadolstudio.data.repository.chronos.main_category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import java.util.UUID

@Entity(tableName = MainCategoryEntity.CATEGORY_TABLE)
data class MainCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: Int
) {
    companion object {
        const val CATEGORY_TABLE = "category_table"
    }
}

fun MainCategoryDomain.toEntity(): MainCategoryEntity = MainCategoryEntity(
    id = id,
    uuid = uuid,
    name = name,
    color = color
)

fun MainCategoryEntity.toDomain(
    subcategoryList: List<SubcategoryDomain> = emptyList()
): MainCategoryDomain =
    MainCategoryDomain(
        id = id,
        uuid = uuid,
        name = name,
        color = color,
        subcategoryList = subcategoryList
    )