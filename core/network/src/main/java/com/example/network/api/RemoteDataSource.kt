package com.example.network.api

interface RemoteDataSource {
    suspend fun getProducts(
        limit: Int,
        skip: Int,
        select: String
    ): ProductsResponse

    suspend fun getProductInfo(
        id: Long,
        select: String
    ): ProductInfo
}