package com.example.di

import android.content.Context
import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
internal interface AppGraph {
    val remoteDataSource: RemoteDataSource

    val localDataSource: LocalDataSource

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides context: Context
        ): AppGraph
    }

}