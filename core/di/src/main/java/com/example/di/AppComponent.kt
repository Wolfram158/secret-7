package com.example.di

import com.example.network.api.RemoteDataSource

interface AppComponent {
    val remoteDataSource: RemoteDataSource
}