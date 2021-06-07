package com.kaiahealth.demo

import android.app.Application
import com.kaiahealth.demo.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@DemoApplication)
            modules(listOf(appModule))
        }
    }
}