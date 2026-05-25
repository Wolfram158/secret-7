package com.example.di

import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource

interface AppComponent {
    val remoteDataSource: RemoteDataSource

    val localDataSource: LocalDataSource
}