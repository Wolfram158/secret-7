package com.example.network.api

class MockRemoteDataSource : RemoteDataSource {
    lateinit var getProducts: () -> ProductsResponse
    lateinit var getProductInfo: () -> ProductInfo

    override suspend fun getProducts(
        limit: Int,
        skip: Int,
        select: String
    ): ProductsResponse = getProducts()

    override suspend fun getProductInfo(
        id: Long,
        select: String
    ): ProductInfo = getProductInfo()
}