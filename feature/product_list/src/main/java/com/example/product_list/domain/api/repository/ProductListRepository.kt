package com.example.product_list.domain.api.repository

import com.example.product_list.domain.api.model.ShortProductInfo

internal interface ProductListRepository {
    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): List<ShortProductInfo>
}