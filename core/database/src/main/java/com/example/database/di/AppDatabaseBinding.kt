package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.impl.AppDatabase
import com.example.database.impl.CartDao
import com.example.database.impl.ProductDetailsDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
@BindingContainer
object AppDatabaseBinding {
    @SingleIn(AppScope::class)
    @Provides
    internal fun provideAppDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @SingleIn(AppScope::class)
    @Provides
    internal fun provideProductDetailsDao(database: AppDatabase): ProductDetailsDao {
        return database.productDetailsDao()
    }

    @SingleIn(AppScope::class)
    @Provides
    internal fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }
}