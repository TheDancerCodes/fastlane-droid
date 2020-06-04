package com.thedancercodes.fastlanedroid.todoapp.statistics

import com.thedancercodes.fastlanedroid.todoapp.data.Task
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Ignore
import org.junit.Test

/**
 * Unit tests for [getActiveAndCompletedStats].
 */
class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_error() {
        // When there's an error loading stats
        // TODO

        // Both active and completed tasks are 0
        // TODO
    }

    @Test
    fun getActiveAndCompletedStats_empty() {
        // When there are no tasks
        // TODO

        // Both active and completed tasks are 0
        // TODO
    }

    @Test
    fun getActiveAndCompletedStats_noCompleted() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed with an active task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 100 and 0
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true)
        )
        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 0 and 100
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_both() {
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }

    @Test
    @Ignore
    fun getActiveAndCompletedStats_error_solution() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    @Ignore
    fun getActiveAndCompletedStats_empty_solution() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}
