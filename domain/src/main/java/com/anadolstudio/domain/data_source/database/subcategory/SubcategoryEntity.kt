package com.anadolstudio.domain.data_source.database.subcategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = SubcategoryEntity.SUBCATEGORY_TABLE)
data class SubcategoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "subcategory_id") val subcategoryId: UUID,
        @ColumnInfo(name = "category_id") val categoryId: UUID,
        @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        const val SUBCATEGORY_TABLE = "subcategory_table"
    }
}
