package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.impl.Migrations.MIGRATION_1_2
import com.example.database.impl.AppDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class, replaces = [AppDatabaseTestImplBinding::class])
@BindingContainer
object AppDatabaseImplBinding {
    @SingleIn(AppScope::class)
    @Provides
    internal fun provideAppDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}