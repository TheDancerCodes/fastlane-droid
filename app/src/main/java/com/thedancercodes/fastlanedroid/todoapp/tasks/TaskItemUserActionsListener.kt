package com.thedancercodes.fastlanedroid.todoapp.tasks

import android.view.View

import com.thedancercodes.fastlanedroid.todoapp.data.Task

/**
 * Listener used with data binding to process user actions.
 */
interface TaskItemUserActionsListener {
    fun onCompleteChanged(task: Task, v: View)

    fun onTaskClicked(task: Task)
}