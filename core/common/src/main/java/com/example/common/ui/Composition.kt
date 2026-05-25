package com.example.common.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.di.AppComponent

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided")
}