package com.thedancercodes.fastlanedroid.todoapp.util


/**
 * Various extension functions for Fragment.
 */

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.thedancercodes.fastlanedroid.todoapp.TodoApplication
import com.thedancercodes.fastlanedroid.todoapp.ViewModelFactory

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val repository = (requireContext().applicationContext as TodoApplication).taskRepository
    return ViewModelProviders.of(this, ViewModelFactory(repository)).get(viewModelClass)
}