package com.thedancercodes.fastlanedroid.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thedancercodes.fastlanedroid.todoapp.addedittask.AddEditTaskViewModel
import com.thedancercodes.fastlanedroid.todoapp.data.source.TasksRepository
import com.thedancercodes.fastlanedroid.todoapp.statistics.StatisticsViewModel
import com.thedancercodes.fastlanedroid.todoapp.taskdetail.TaskDetailViewModel
import com.thedancercodes.fastlanedroid.todoapp.tasks.TasksViewModel

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
class ViewModelFactory constructor(
    private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(StatisticsViewModel::class.java) ->
                    StatisticsViewModel(tasksRepository)
                isAssignableFrom(TaskDetailViewModel::class.java) ->
                    TaskDetailViewModel(tasksRepository)
                isAssignableFrom(AddEditTaskViewModel::class.java) ->
                    AddEditTaskViewModel(tasksRepository)
                isAssignableFrom(TasksViewModel::class.java) ->
                    TasksViewModel(tasksRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}