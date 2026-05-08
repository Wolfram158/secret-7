package com.example.di

import com.example.network.api.RemoteDataSource
import dev.zacsweers.metro.createGraph

object AppComponentProvider {
    fun provideAppComponent(): AppComponent {
        val graph = createGraph<AppGraph>()
        return object : AppComponent {
            override val remoteDataSource: RemoteDataSource = graph.remoteDataSource
        }
    }
}