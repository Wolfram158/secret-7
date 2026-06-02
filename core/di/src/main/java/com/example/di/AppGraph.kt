package com.example.di

import android.content.Context
import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource
import com.example.settings.api.SettingsDataSource
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
internal interface AppGraph {
    val remoteDataSource: RemoteDataSource

    val localDataSource: LocalDataSource

    val settingsDataSource: SettingsDataSource

    val defaultScope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides context: Context
        ): AppGraph
    }

}