package com.example.database.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_details")
data class ProductDetailsDbo(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @ColumnInfo("thumbnail") val thumbnail: String,
    @ColumnInfo("updated_at") val updatedAt: Long,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("rating") val rating: Double,
    @ColumnInfo("price") val price: Double,
    @ColumnInfo("weight") val weight: Double,
    @ColumnInfo("availability_status") val availabilityStatus: String,
    @ColumnInfo("warranty_information") val warrantyInformation: String
)