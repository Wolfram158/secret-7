package com.example.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.api.CartElementDbo
import com.example.database.api.ProductDetailsDbo

@Database(
    entities = [
        ProductDetailsDbo::class,
        CartElementDbo::class
    ],
    version = 2
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun productDetailsDao(): ProductDetailsDao

    abstract fun cartDao(): CartDao
}
