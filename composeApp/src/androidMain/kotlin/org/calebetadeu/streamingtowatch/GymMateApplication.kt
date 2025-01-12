package org.calebetadeu.streamingtowatch

import android.app.Application
import org.calebetadeu.streamingtowatch.di.initKoin
import org.koin.android.ext.koin.androidContext

class GymMateApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GymMateApplication)
        }
    }
}