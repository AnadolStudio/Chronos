package com.anadolstudio.domain.data_source.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.domain.data_source.database.task.TaskEntity.Companion.TASK_TABLE
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE")
    fun getAllTask(): Single<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(category: TaskEntity): Completable

    @Query("UPDATE $TASK_TABLE SET minutes = :minutes WHERE task_id = :taskId AND day = :day")
    fun updateTask(taskId: UUID, minutes: Int, day: DateTime): Single<Int>

    @Query("SELECT * FROM $TASK_TABLE WHERE task_id = :taskId")
    fun getTaskById(taskId: String): Single<TaskEntity>

    @Query("SELECT * FROM $TASK_TABLE WHERE day = :day")
    fun getTasksByDay(day: DateTime): Single<List<TaskEntity>>

    @Delete
    fun deleteTask(taskEntity: TaskEntity): Completable

}
