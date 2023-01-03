package com.anadolstudio.data.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.data.database.task.TaskEntity.Companion.TASK_TABLE
import org.joda.time.DateTime
import java.util.UUID

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE")
    fun getAllTask(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(category: TaskEntity)

    @Query("UPDATE $TASK_TABLE SET minutes = :minutes WHERE task_id = :taskId AND day = :day")
    fun updateTask(taskId: UUID, minutes: Int, day: DateTime): Int

    @Query("SELECT * FROM $TASK_TABLE WHERE task_id = :taskId")
    fun getTaskById(taskId: String): TaskEntity

    @Query("SELECT * FROM $TASK_TABLE WHERE day = :day")
    fun getTasksByDay(day: DateTime): List<TaskEntity>

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

}
