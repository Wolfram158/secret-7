package com.example.common

import android.app.Application
import android.content.Context
import com.example.di.AppComponentProvider
import kotlinx.coroutines.Dispatchers
import money.vivid.elmslie.core.config.ElmslieConfig

class App : Application() {
    val appComponent by lazy(LazyThreadSafetyMode.NONE) {
        AppComponentProvider.provideAppComponent(this)
    }

    override fun onCreate() {
        super.onCreate()
        ElmslieConfig.elmDispatcher {
            Dispatchers.Default
        }
        appComponent
    }
}

fun Context.getAppComponent() = (applicationContext as App).appComponent