package com.thedancercodes.fastlanedroid.todoapp.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thedancercodes.fastlanedroid.todoapp.EventObserver
import com.thedancercodes.fastlanedroid.todoapp.R
import com.thedancercodes.fastlanedroid.todoapp.databinding.AddtaskFragBinding
import com.thedancercodes.fastlanedroid.todoapp.util.ADD_EDIT_RESULT_OK
import com.thedancercodes.fastlanedroid.todoapp.util.obtainViewModel
import com.thedancercodes.fastlanedroid.todoapp.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class AddEditTaskFragment : Fragment() {

    private lateinit var viewDataBinding: AddtaskFragBinding

    private lateinit var viewModel: AddEditTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.addtask_frag, container, false)
        viewModel = obtainViewModel(AddEditTaskViewModel::class.java)
        viewDataBinding = AddtaskFragBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupNavigation()
        loadData()
    }

    private fun setupSnackbar() {
        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_SHORT)
        }
    }

    private fun setupNavigation() {
        viewModel.taskUpdatedEvent.observe(this, EventObserver {
            val action = AddEditTaskFragmentDirections
                .actionAddEditTaskFragmentToTasksFragment(ADD_EDIT_RESULT_OK)
            findNavController().navigate(action)
        })
    }

    private fun loadData() {
        // Add or edit an existing task?
        viewDataBinding.viewmodel?.start(getTaskId())
    }

    private fun getTaskId(): String? {
        return arguments?.let {
            AddEditTaskFragmentArgs.fromBundle(it).TASKID
        }
    }
}
