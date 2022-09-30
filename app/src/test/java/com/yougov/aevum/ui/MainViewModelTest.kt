package com.yougov.aevum.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yougov.aevum.data.Timer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    lateinit var viewModel: MainViewModel

    @Spy
    lateinit var timer: Timer

    @Before
    fun init() {
        viewModel = MainViewModel()
        timer = Timer()
        openMocks(this)
    }

    @Test
    fun `Test Calculate Time in Millis for empty params`() {
        val timeInMillis = viewModel.calculateTimerInMillis("", "", "")
        assertEquals(0, timeInMillis)
    }

    @Test
    fun `Test Calculate Time in Millis for empty hours`() {
        val timeInMillis = viewModel.calculateTimerInMillis("", "12", "22")
        assertEquals(742000, timeInMillis)
    }

    @Test
    fun `Test Calculate Time in Millis for empty minutes`() {
        val timeInMillis = viewModel.calculateTimerInMillis("1", "", "22")
        assertEquals(3622000, timeInMillis)
    }

    @Test
    fun `Test Calculate Time in Millis for empty seconds`() {
        val timeInMillis = viewModel.calculateTimerInMillis("2", "11", "")
        assertEquals(7860000, timeInMillis)
    }

    @Test
    fun `Test timer is added to the timers list`() {
        viewModel.addNewTimer(timer)
        assertEquals(1, viewModel.timers.size)
        assertEquals(timer, viewModel.timers[0])
    }

    @Test
    fun `Test timer is removed from the timers list`() {
        viewModel.addNewTimer(timer)
        viewModel.addNewTimer(timer)

        assertEquals(2, viewModel.timers.size)
        assertEquals(timer, viewModel.timers[0])

        viewModel.removeTimer(timer)

        assertEquals(1, viewModel.timers.size)
    }
}