package com.anadolstudio.data.repository.task

import com.anadolstudio.domain.data_source.database.task.TaskDao
import com.anadolstudio.domain.data_source.database.task.TaskEntity
import com.anadolstudio.domain.repository.task.TaskRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override fun getAllTask(): Single<List<TaskEntity>> = taskDao
            .getAllTask()

    override fun insertTask(category: TaskEntity): Completable = taskDao
            .insertTask(category)

    override fun updateTask(taskId: UUID, minutes: Int, day: DateTime): Single<Int> = taskDao
            .updateTask(taskId, minutes, day)

    override fun getTaskById(taskId: String): Single<TaskEntity> = taskDao
            .getTaskById(taskId)

    override fun getTasksByDay(day: DateTime): Single<List<TaskEntity>> = taskDao
            .getTasksByDay(day)

    override fun deleteTask(taskEntity: TaskEntity): Completable = taskDao
            .deleteTask(taskEntity)
}
