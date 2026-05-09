package com.example.product_list.data.repository

import com.example.network.api.ProductsResponse
import com.example.product_list.di.ProductListScope
import com.example.product_list.domain.api.model.ShortProductInfo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import com.example.network.api.ShortProductInfo as ShortProductInfoDto

@SingleIn(ProductListScope::class)
@Inject
internal class ProductListMapper {
    infix fun mapToDomain(productsResponse: ProductsResponse) =
        productsResponse.products.map { this mapToDomain it }

    infix fun mapToDomain(productInfo: ShortProductInfoDto) = with(productInfo) {
        ShortProductInfo(
            id = id,
            title = title,
            price = price,
            brand = brand
        )
    }
}