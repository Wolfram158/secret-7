package com.example.network.impl

import com.example.network.api.ProductInfo
import com.example.network.api.ProductsResponse
import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitDataSource : RemoteDataSource {
    @GET("products")
    override suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("select") select: List<@JvmSuppressWildcards Selector>
    ): ProductsResponse

    @GET("products/{id}")
    override suspend fun getProductInfo(
        @Path("id") id: Long,
        @Query("select") select: List<@JvmSuppressWildcards Selector>
    ): ProductInfo
}