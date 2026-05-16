package com.example.product_details.data.repository

import com.example.database.api.ProductDetailsDbo
import com.example.network.api.ProductInfo
import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.model.ProductDetails
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(ProductDetailsScope::class)
@Inject
internal class ProductDetailsMapper {
    fun mapToDomain(
        productInfo: ProductInfo,
        isActual: Boolean
    ) = with(productInfo) {
        ProductDetails(
            id = id,
            isActual = isActual,
            title = title,
            description = description,
            rating = rating,
            price = price,
            weight = weight,
            availabilityStatus = availabilityStatus,
            warrantyInformation = warrantyInformation,
            thumbnail = thumbnail
        )
    }

    fun mapToDomain(
        productDetailsDbo: ProductDetailsDbo,
        isActual: Boolean
    ) = with(productDetailsDbo) {
        ProductDetails(
            id = id,
            isActual = isActual,
            title = title,
            description = description,
            rating = rating,
            price = price,
            weight = weight,
            availabilityStatus = availabilityStatus,
            warrantyInformation = warrantyInformation,
            thumbnail = thumbnail
        )
    }

    fun mapRemoteToLocal(
        id: Long,
        productInfo: ProductInfo
    ) = with(productInfo) {
        ProductDetailsDbo(
            id = id,
            updatedAt = System.currentTimeMillis(),
            title = title,
            description = description,
            rating = rating,
            price = price,
            weight = weight,
            availabilityStatus = availabilityStatus,
            warrantyInformation = warrantyInformation,
            thumbnail = thumbnail
        )
    }
}