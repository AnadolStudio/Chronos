package com.anadolstudio.data.model.subcategory_object

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anadolstudio.data.model.subcategory.SubcategoryEntity
import java.util.UUID

@Entity(tableName = SubcategoryObjectEntity.SUBCATEGORY_OBJECT_TABLE)
data class SubcategoryObjectEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "category_id") val categoryId: UUID,
        @ColumnInfo(name = "subcategory_id") val subcategoryId: UUID,
        @ColumnInfo(name = "object_id") val objectId: UUID,
        @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        const val SUBCATEGORY_OBJECT_TABLE = "subcategory_object_table"
    }
}
