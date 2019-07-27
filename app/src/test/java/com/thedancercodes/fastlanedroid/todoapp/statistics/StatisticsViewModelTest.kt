package com.thedancercodes.fastlanedroid.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thedancercodes.fastlanedroid.todoapp.FakeFailingTasksRemoteDataSource
import com.thedancercodes.fastlanedroid.todoapp.LiveDataTestUtil
import com.thedancercodes.fastlanedroid.todoapp.MainCoroutineRule
import com.thedancercodes.fastlanedroid.todoapp.data.Task
import com.thedancercodes.fastlanedroid.todoapp.data.source.DefaultTasksRepository
import com.thedancercodes.fastlanedroid.todoapp.data.source.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [StatisticsViewModel]
 */
@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the viewmodel
    private val tasksRepository = FakeRepository()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupStatisticsViewModel() {
        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadEmptyTasksFromRepository_EmptyResults() = mainCoroutineRule.runBlockingTest {
        // Given an initialized StatisticsViewModel with no tasks

        // When loading of Tasks is requested
        statisticsViewModel.start()

        // Then the results are empty
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.empty)).isTrue()
    }

    @Test
    fun loadNonEmptyTasksFromRepository_NonEmptyResults() {
        // We initialise the tasks to 3, with one active and two completed
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        val task4 = Task("Title4", "Description4", true)
        tasksRepository.addTasks(task1, task2, task3, task4)

        // When loading of Tasks is requested
        statisticsViewModel.start()

        // Then the results are not empty
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.empty))
            .isFalse()
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.activeTasksPercent))
            .isEqualTo(25f)
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.completedTasksPercent))
            .isEqualTo(75f)
    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_CallErrorToDisplay() =
        mainCoroutineRule.runBlockingTest {
            val errorViewModel = StatisticsViewModel(
                DefaultTasksRepository(
                    FakeFailingTasksRemoteDataSource,
                    FakeFailingTasksRemoteDataSource,
                    Dispatchers.Main  // Main is set in MainCoroutineRule
                )
            )

            // When statistics are loaded
            errorViewModel.start()

            // Then an error message is shown
            assertThat(LiveDataTestUtil.getValue(errorViewModel.empty)).isTrue()
            assertThat(LiveDataTestUtil.getValue(errorViewModel.error)).isTrue()
        }

    @Test
    fun loadTasks_loading() {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the viewmodel
        statisticsViewModel.start()

        // Then progress indicator is shown
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        assertThat(LiveDataTestUtil.getValue(statisticsViewModel.dataLoading)).isFalse()
    }
}
