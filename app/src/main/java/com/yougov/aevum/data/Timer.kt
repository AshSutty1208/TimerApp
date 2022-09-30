package com.yougov.aevum.data

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.TimeUnit

/**
 * Timer class represents a CountDownTimer with some helper functions for use in the View
 */
class Timer {
    private lateinit var timer: CountDownTimer
    private var paused: Boolean = false
    private var timeRemaining: Long = 0

    var timerText by mutableStateOf("")
        private set

    /**
     * Creates a new CountDownTimer and assigns it to the timer variable
     *
     * @param totalMillis - Total amount of time the timer needs to be in Milliseconds as a Long
     */
    fun startTimer(totalMillis: Long) {
        timer = object: CountDownTimer(totalMillis, 1000) {
            override fun onTick(p0: Long) {
                onTimerTick(p0)
            }

            override fun onFinish() {
            }

        }.start()
    }

    fun onTimerTick(millis: Long) {
        if (paused) {
            timeRemaining = millis
            timer.cancel()
        } else {
            timerText = getTimeTextFormatted(millis)
        }
    }

    /**
     * Restarts the timer by creating a new CountDownTimer and setting the pause variable to false
     */
    fun resumeTimer() {
        paused = false
        startTimer(timeRemaining)
    }

    /**
     * Pauses the timer by setting the paused variable.
     *
     * This tells the currently running CountDownTimer that it is paused.
     *
     * This results in the setting of the timeRemaining variable to save the amount that is left
     * also cancelling the current timer.
     */
    fun pauseTimer() {
        paused = true
    }

    /**
     * Calls the cancel function on the current timer
     */
    fun cancelTimer() {
        timer.cancel()
    }

    /**
     * Returns a time in millis as a String in the format HH:MM:SS
     *
     * @param millis - amount of time in milliseconds as a Long
     * @return Formatted Time in HH:MM:SS as a String
     */
    fun getTimeTextFormatted(millis: Long) : String {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
    }


    /**
     * Returns the state of the paused variable
     */
    fun isPaused(): Boolean {
        return paused
    }
}