package com.yougov.aevum.ui

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.yougov.aevum.GlobalConsts
import com.yougov.aevum.data.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var timers = mutableStateListOf<Timer>()
        private set

    /**
     * Adds a timer to the timers variable state
     *
     * This triggers the composable in the view to recompose the list of timers
     *
     * @param hours - Amount of hours as a String
     * @param minutes - Amount of minutes as a String
     * @param seconds - Amount of seconds as a String
     */
    fun addNewTimer(timer: Timer) {
        timers.add(timer)
    }

    /**
     * Cancels the given timer and removes a timer from the timers variable state
     *
     * This triggers the composable in the view to recompose the list of timers
     *
     * @param timer - The timer which the "Remove" button has been clicked
     */
    fun removeTimer(timer: Timer) {
        timers.remove(timer)
    }

    /**
     * Converts hours, minutes and seconds as a String to a length of time in millis
     *
     * @param hours - How many hours as a String
     * @param minutes - How many minutes as a String
     * @param seconds - How many seconds as a String
     *
     * @return Returns values combined as a Long in Milliseconds
     */
    fun calculateTimerInMillis(hours: String, minutes: String, seconds: String): Long {
        var hoursToMillis: Long = 0
        if (hours.isNotEmpty()) {
            hoursToMillis = hours.toLong() * GlobalConsts.HOUR_IN_MILLIS
        }

        var minutesToMillis: Long = 0
        if (minutes.isNotEmpty()) {
            minutesToMillis = minutes.toLong() * GlobalConsts.MINUTE_IN_MILLIS
        }

        var secondsToMillis: Long = 0
        if (seconds.isNotEmpty()) {
            secondsToMillis = seconds.toLong() * GlobalConsts.SECOND_IN_MILLIS
        }

        return hoursToMillis + minutesToMillis + secondsToMillis
    }
}