package com.example.product_list.domain.api.usecase

import com.example.product_list.domain.api.model.ShortProductInfo

internal interface GetProductsUseCase {
    suspend operator fun invoke(
        limit: Int,
        skip: Int
    ): List<ShortProductInfo>
}