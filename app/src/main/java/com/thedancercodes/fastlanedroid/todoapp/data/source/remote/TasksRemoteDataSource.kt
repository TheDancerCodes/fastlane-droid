package com.thedancercodes.fastlanedroid.todoapp.data.source.remote

import com.thedancercodes.fastlanedroid.todoapp.data.Result
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Error
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Success
import com.thedancercodes.fastlanedroid.todoapp.data.Task
import com.thedancercodes.fastlanedroid.todoapp.data.source.TasksDataSource
import com.google.common.collect.Lists
import kotlinx.coroutines.delay

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object TasksRemoteDataSource : TasksDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    /**
     * Note: [LoadTasksCallback.onDataNotAvailable] is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    override suspend fun getTasks(): Result<List<Task>> {
        // Simulate network by delaying the execution.
        val tasks = Lists.newArrayList(TASKS_SERVICE_DATA.values)
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(tasks)
    }

    /**
     * Note: [GetTaskCallback.onDataNotAvailable] is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    override suspend fun getTask(taskId: String): Result<Task> {

        // Simulate network by delaying the execution.
        delay(SERVICE_LATENCY_IN_MILLIS)
        TASKS_SERVICE_DATA[taskId]?.let {
            return Success(it)
        }
        return Error(Exception("Task not found"))
    }

    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA.put(newTask.id, newTask)
    }
    override suspend fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.id, task)
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, true, task.id)
        TASKS_SERVICE_DATA.put(task.id, completedTask)
    }

    override suspend fun completeTask(taskId: String) {
        // Not required for the remote data source because the {@link DefaultTasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override suspend fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, false, task.id)
        TASKS_SERVICE_DATA.put(task.id, activeTask)
    }

    override suspend fun activateTask(taskId: String) {
        // Not required for the remote data source because the {@link DefaultTasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override suspend fun clearCompletedTasks() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
    }

    override suspend fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }
}
