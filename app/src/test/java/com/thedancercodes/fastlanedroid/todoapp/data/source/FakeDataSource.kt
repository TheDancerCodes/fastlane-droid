package com.thedancercodes.fastlanedroid.todoapp.data.source

import com.thedancercodes.fastlanedroid.todoapp.data.Result
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Error
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Success
import com.thedancercodes.fastlanedroid.todoapp.data.Task

class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) :
    TasksDataSource {

    override suspend fun getTasks(): Result<List<Task>> {
        tasks?.let { return Success(it) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        tasks?.firstOrNull { it.id == taskId }?.let { return Success(it) }
        return Error(
            Exception("Task not found")
        )
    }

    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun completeTask(task: Task) {
        tasks?.firstOrNull { it.id == task.id }?.let { it.isCompleted = true }
    }

    override suspend fun completeTask(taskId: String) {
        tasks?.firstOrNull { it.id == taskId }?.let { it.isCompleted = true }
    }

    override suspend fun activateTask(task: Task) {
        tasks?.firstOrNull { it.id == task.id }?.let { it.isCompleted = false }
    }

    override suspend fun activateTask(taskId: String) {
        tasks?.firstOrNull { it.id == taskId }?.let { it.isCompleted = false }
    }

    override suspend fun clearCompletedTasks() {
        tasks?.removeIf { it.isCompleted }
    }

    override suspend fun deleteAllTasks() {
        tasks = mutableListOf()
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.removeIf { it.id == taskId }
    }
}