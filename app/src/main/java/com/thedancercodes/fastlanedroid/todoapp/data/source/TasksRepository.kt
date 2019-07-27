package com.thedancercodes.fastlanedroid.todoapp.data.source

import com.thedancercodes.fastlanedroid.todoapp.data.Result
import com.thedancercodes.fastlanedroid.todoapp.data.Task

interface TasksRepository {

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task>

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}