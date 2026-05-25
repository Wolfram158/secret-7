package com.example.network.api

interface RemoteDataSource {
    suspend fun getProducts(
        limit: Int,
        skip: Int,
        select: List<Selector>
    ): ProductsResponse

    suspend fun getProductInfo(
        id: Long,
        select: List<Selector>
    ): ProductInfo
}