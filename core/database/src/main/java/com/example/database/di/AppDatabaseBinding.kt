package com.example.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    internal val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "create table if not exists " +
                        "cart(id bigint not null primary key, " +
                        "title text not null, " +
                        "count bigint not null)"
            )
        }
    }

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