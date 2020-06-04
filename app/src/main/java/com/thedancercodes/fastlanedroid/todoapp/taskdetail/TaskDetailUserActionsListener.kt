package com.thedancercodes.fastlanedroid.todoapp.taskdetail

import android.view.View

/**
 * Listener used with data binding to process user actions.
 */
interface TaskDetailUserActionsListener {

    fun onCompleteChanged(v: View)
}