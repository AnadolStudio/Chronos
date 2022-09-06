package com.anadolstudio.data.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Category.CATEGORY_TABLE)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "total_minutes") val totalMinutes: String,
    @ColumnInfo(name = "child_id") val childId: String?
) {
    companion object {
        const val CATEGORY_TABLE = "category_table"
    }
}
