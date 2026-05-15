package com.example.database.api

interface ProductDetailsLocalDataSource {
    suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo?

    suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo)
}