package com.example.product_details.data.repository

import com.example.network.api.ProductInfo
import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.model.ProductDetails
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(ProductDetailsScope::class)
@Inject
internal class ProductDetailsMapper {
    infix fun mapToDomain(productInfo: ProductInfo) = with(productInfo) {
        ProductDetails(
            title = title,
            description = description,
            rating = rating,
            price = price,
            weight = weight,
            availabilityStatus = availabilityStatus,
            warrantyInformation = warrantyInformation
        )
    }
}