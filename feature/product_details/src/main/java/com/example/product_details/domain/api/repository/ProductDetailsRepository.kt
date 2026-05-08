package com.example.product_details.domain.api.repository

import com.example.product_details.domain.api.model.ProductDetails

internal interface ProductDetailsRepository {
    suspend fun getProductDetails(
        id: Long
    ): ProductDetails
}