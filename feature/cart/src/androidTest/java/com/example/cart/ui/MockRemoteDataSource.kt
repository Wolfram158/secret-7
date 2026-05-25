package com.example.cart.ui

import com.example.network.api.ProductInfo
import com.example.network.api.ProductsResponse
import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector

internal class MockRemoteDataSource : RemoteDataSource {
    override suspend fun getProducts(
        limit: Int,
        skip: Int,
        select: List<Selector>
    ): ProductsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProductInfo(
        id: Long,
        select: List<Selector>
    ): ProductInfo {
        TODO("Not yet implemented")
    }

}