package com.example.di

import com.example.network.api.RemoteDataSource
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
internal interface AppGraph {
    val remoteDataSource: RemoteDataSource
}