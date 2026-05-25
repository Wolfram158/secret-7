package com.example.product_details.domain.api.usecase

import com.example.product_details.domain.api.model.ProductDetails

internal interface GetProductDetailsUseCase {
    suspend operator fun invoke(id: Long): ProductDetails
}