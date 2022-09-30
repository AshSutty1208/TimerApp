package com.yougov.aevum

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yougov.aevum.ui.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    val viewModel = MainViewModel()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun `Test Starting Timer with Minutes`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("minutes").performTextInput("1")
        composeTestRule.onNodeWithText("Start!").performClick()
        composeTestRule.onNodeWithTag("timersList").onChildAt(0).assertExists()
    }

    @Test
    fun `Test Starting Timer with Hours`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithText("Start!").performClick()
        composeTestRule.onNodeWithTag("timersList").onChildAt(0).assertExists()
    }

    @Test
    fun `Test Starting Timer with Seconds`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithText("Start!").performClick()
        composeTestRule.onNodeWithTag("timersList").onChildAt(0).assertExists()
    }

    @Test
    fun `Test Starting Timer with All In One`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithTag("minutes").performTextInput("21")
        composeTestRule.onNodeWithText("Start!").performClick()
        composeTestRule.onNodeWithTag("timersList").onChildAt(0).assertExists()
    }

    @Test
    fun `Test Adding more than one timer`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithTag("minutes").performTextInput("21")
        composeTestRule.onNodeWithText("Start!").performClick()

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithTag("minutes").performTextInput("21")
        composeTestRule.onNodeWithText("Start!").performClick()

        // 6 Because there is 3 views in one row
        // Adding two rows will result in 6 children
        composeTestRule.onNodeWithTag("timersList").onChildren().assertCountEquals(6)
    }

    @Test
    fun `Test Removing a timer`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithTag("minutes").performTextInput("21")
        composeTestRule.onNodeWithText("Start!").performClick()

        composeTestRule.onNodeWithTag("timersList").onChildren().assertCountEquals(3)

        composeTestRule.onNodeWithTag("removeButton").performClick()

        composeTestRule.onNodeWithTag("timersList").onChildren().assertCountEquals(0)
    }

    @Test
    fun `Test Pausing and resuming a timer`() {
        composeTestRule.setContent {
            TimersScreen(viewModel = viewModel,
                onAddTimer = { hours, minutes, seconds ->
                    val timer = com.yougov.aevum.data.Timer()
                    viewModel.addNewTimer(timer)
                    timer.startTimer(viewModel.calculateTimerInMillis(hours, minutes, seconds))
                })
        }

        composeTestRule.onNodeWithTag("seconds").performTextInput("10")
        composeTestRule.onNodeWithTag("hours").performTextInput("1")
        composeTestRule.onNodeWithTag("minutes").performTextInput("21")
        composeTestRule.onNodeWithText("Start!").performClick()

        composeTestRule.onNodeWithTag("timersList").onChildren().assertCountEquals(3)

        composeTestRule.onNodeWithTag("pauseResumeButton").performClick()

        //Get test to wait so we can see the pause
        Thread.sleep(3000)

        composeTestRule.onNodeWithTag("pauseResumeButton").performClick()

        //Wait after so we can see it resume
        Thread.sleep(3000)
    }
}