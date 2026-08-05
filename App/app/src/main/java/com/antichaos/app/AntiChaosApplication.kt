package com.antichaos.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AntiChaosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // App initialization will be handled by Hilt modules
    }
}
