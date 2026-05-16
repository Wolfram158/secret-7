package com.example.database.di

import com.example.database.api.LocalDataSource
import com.example.database.impl.AppDatabase
import com.example.database.impl.LocalDataSourceImpl
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
@BindingContainer
object LocalDataSourceImplBinding {
    @SingleIn(AppScope::class)
    @Provides
    internal fun provideLocalDataSource(
        database: AppDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            database.productDetailsDao(),
            database.cartDao(),
            { database.close() }
        )

    }
}