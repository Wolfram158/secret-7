package com.example.di

import android.content.Context
import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource
import com.example.settings.api.SettingsDataSource
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.CoroutineScope

object AppComponentProvider {
    fun provideAppComponent(context: Context): AppComponent {
        val graph = createGraphFactory<AppGraph.Factory>().create(context)
        return object : AppComponent {
            override val remoteDataSource: RemoteDataSource = graph.remoteDataSource
            override val localDataSource: LocalDataSource = graph.localDataSource
            override val settingsDataSource: SettingsDataSource = graph.settingsDataSource
            override val defaultScope: CoroutineScope = graph.defaultScope
        }
    }
}