package com.example.common

import android.app.Application
import android.content.Context
import com.example.di.AppComponentProvider

class App : Application() {
    val appComponent by lazy(LazyThreadSafetyMode.NONE) {
        AppComponentProvider.provideAppComponent(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent
    }
}

fun Context.getAppComponent() = (applicationContext as App).appComponent