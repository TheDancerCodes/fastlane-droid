package com.thedancercodes.fastlanedroid.todoapp.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thedancercodes.fastlanedroid.todoapp.data.Result.Success
import com.thedancercodes.fastlanedroid.todoapp.data.Task
import com.thedancercodes.fastlanedroid.todoapp.data.source.TasksRepository
import com.thedancercodes.fastlanedroid.todoapp.util.EspressoIdlingResource
import kotlinx.coroutines.launch

/**
 * Exposes the data to be used in the statistics screen.
 *
 *
 * This ViewModel uses both [ObservableField]s ([ObservableBoolean]s in this case) and
 * [Bindable] getters. The values in [ObservableField]s are used directly in the layout,
 * whereas the [Bindable] getters allow us to add some logic to it. This is
 * preferable to having logic in the XML layout.
 */
class StatisticsViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    /**
     * Controls whether the stats are shown or a "No data" message.
     */
    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    private val _activeTasksPercent = MutableLiveData<Float>()
    val activeTasksPercent: LiveData<Float> = _activeTasksPercent

    private val _completedTasksPercent = MutableLiveData<Float>()
    val completedTasksPercent: LiveData<Float> = _completedTasksPercent

    private var activeTasks = 0

    private var completedTasks = 0

    fun start() {
        _dataLoading.value = true

        // Espresso does not work well with coroutines yet. See
        // https://github.com/Kotlin/kotlinx.coroutines/issues/982
        EspressoIdlingResource.increment() // Set app as busy.

        viewModelScope.launch {
            tasksRepository.getTasks().let { result ->
                if (result is Success) {
                    _error.value = false
                    computeStats(result.data)
                } else {
                    _error.value = true
                    activeTasks = 0
                    completedTasks = 0
                    computeStats(null)
                }
            }
        }
    }

    /**
     * Called when new data is ready.
     */
    private fun computeStats(tasks: List<Task>?) {
        getActiveAndCompletedStats(tasks).let {
            _activeTasksPercent.value = it.activeTasksPercent
            _completedTasksPercent.value = it.completedTasksPercent
        }
        _empty.value = tasks.isNullOrEmpty()
        _dataLoading.value = false
        EspressoIdlingResource.decrement() // Set app as idle.
    }
}
