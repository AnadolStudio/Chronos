package com.anadolstudio.domain.data_source.database.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.util.UUID

@Entity(tableName = TaskEntity.TASK_TABLE)
data class TaskEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "category_id") val categoryId: UUID,
        @ColumnInfo(name = "subcategory_id") val subcategoryId: UUID,
        @ColumnInfo(name = "object_id") val objectId: UUID? = null,
        @ColumnInfo(name = "task_id") val taskId: UUID,
        @ColumnInfo(name = "day") val day: DateTime,
        @ColumnInfo(name = "minutes") val minutes: Int,
) {
    companion object {
        const val TASK_TABLE = "task_table"
    }
}
