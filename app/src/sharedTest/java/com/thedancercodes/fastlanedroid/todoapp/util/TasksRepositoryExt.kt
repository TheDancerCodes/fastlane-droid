package com.thedancercodes.fastlanedroid.todoapp.util

import com.thedancercodes.fastlanedroid.todoapp.data.Task
import com.thedancercodes.fastlanedroid.todoapp.data.source.TasksRepository
import kotlinx.coroutines.runBlocking

/**
 * A blocking version of TasksRepository.saveTask to minimize the number of times we have to
 * explicitly add <code>runBlocking { ... }</code> in our tests
 */
fun TasksRepository.saveTaskBlocking(task: Task) = kotlinx.coroutines.runBlocking {
    this@saveTaskBlocking.saveTask(task)
}

fun TasksRepository.getTasksBlocking(forceUpdate: Boolean) = kotlinx.coroutines.runBlocking {
    this@getTasksBlocking.getTasks(forceUpdate)
}

fun TasksRepository.deleteAllTasksBlocking() = kotlinx.coroutines.runBlocking {
    this@deleteAllTasksBlocking.deleteAllTasks()
}