package com.example.database.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartElementDbo(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("count") val count: Long
)
