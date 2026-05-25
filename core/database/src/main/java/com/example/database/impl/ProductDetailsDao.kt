package com.example.database.impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.api.ProductDetailsLocalDataSource
import com.example.database.api.ProductDetailsDbo

@Dao
internal interface ProductDetailsDao : ProductDetailsLocalDataSource {
    @Query("select * from product_details where id = :id")
    override suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo)
}