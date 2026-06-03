package com.example.di

import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource
import com.example.settings.api.SettingsDataSource
import kotlinx.coroutines.CoroutineScope

interface AppComponent {
    val remoteDataSource: RemoteDataSource
    val localDataSource: LocalDataSource
    val settingsDataSource: SettingsDataSource
    val defaultScope: CoroutineScope
}