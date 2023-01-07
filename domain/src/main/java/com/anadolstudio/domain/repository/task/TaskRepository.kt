package com.anadolstudio.domain.repository.task

import com.anadolstudio.domain.data_source.database.task.TaskEntity
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

interface TaskRepository {

    fun getAllTask(): Single<List<TaskEntity>>

    fun insertTask(category: TaskEntity): Completable

    fun updateTask(taskId: UUID, minutes: Int, day: DateTime): Single<Int>

    fun getTaskById(taskId: String): Single<TaskEntity>

    fun getTasksByDay(day: DateTime): Single<List<TaskEntity>>

    fun deleteTask(taskEntity: TaskEntity): Completable

}
