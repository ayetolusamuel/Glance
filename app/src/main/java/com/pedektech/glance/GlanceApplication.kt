package com.pedektech.glance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class GlanceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging (only in debug builds)
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }

        Timber.d("GlanceApplication initialized")
    }
}