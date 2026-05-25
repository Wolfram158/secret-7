package com.example.database.impl

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "create table if not exists " +
                        "cart(id bigint not null primary key, " +
                        "title text not null, " +
                        "count bigint not null)"
            )
        }
    }
}