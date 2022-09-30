package com.yougov.aevum.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * When using Hilt any Android app must extend an HiltAndroidApp
 *
 * Therefore we create a custom Application class with HiltAndroidApp annotation
 */
@HiltAndroidApp
open class BaseApplication : Application() {
}