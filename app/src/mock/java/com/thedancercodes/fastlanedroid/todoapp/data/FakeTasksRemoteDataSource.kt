package com.thedancercodes.fastlanedroid.todoapp.data

import com.thedancercodes.fastlanedroid.todoapp.data.Result.Error
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Success
import com.thedancercodes.fastlanedroid.todoapp.data.source.TasksDataSource
import com.google.common.collect.Lists
import java.util.LinkedHashMap

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
object FakeTasksRemoteDataSource : TasksDataSource {

    private var TASKS_SERVICE_DATA: LinkedHashMap<String, Task> = LinkedHashMap()

    override suspend fun getTask(taskId: String): Result<Task> {
        TASKS_SERVICE_DATA[taskId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find task"))
    }

    override suspend fun getTasks(): Result<List<Task>> {
        return Success(Lists.newArrayList(TASKS_SERVICE_DATA.values))
    }

    override suspend fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.id, task)
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, true, task.id)
        TASKS_SERVICE_DATA.put(task.id, completedTask)
    }

    override suspend fun completeTask(taskId: String) {
        // Not required for the remote data source.
    }

    override suspend fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, false, task.id)
        TASKS_SERVICE_DATA.put(task.id, activeTask)
    }

    override suspend fun activateTask(taskId: String) {
        // Not required for the remote data source.
    }

    override suspend fun clearCompletedTasks() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
    }

    override suspend fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }

    override suspend fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }
}
