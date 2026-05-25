package com.example.network.api

class MockRemoteDataSource : RemoteDataSource {
    lateinit var getProducts: () -> ProductsResponse
    lateinit var getProductInfo: () -> ProductInfo

    override suspend fun getProducts(
        limit: Int,
        skip: Int,
        select: List<Selector>
    ): ProductsResponse = getProducts()

    override suspend fun getProductInfo(
        id: Long,
        select: List<Selector>
    ): ProductInfo = getProductInfo()
}