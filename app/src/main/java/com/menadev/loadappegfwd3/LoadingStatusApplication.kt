package com.menadev.loadappegfwd3

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber

class LoadingStatusApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        BuildConfig.DEBUG.takeIf { it }.let { Timber.plant(Timber.DebugTree()) }

    }
}