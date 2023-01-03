package com.anadolstudio.domain.model.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = CategoryEntity.CATEGORY_TABLE)
data class CategoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "category_id") val categoryId: UUID,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "color") val color: Int
) {
    companion object {
        const val CATEGORY_TABLE = "category_table"
    }
}
