package com.example.product_details.data.repository

import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector
import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.model.ProductDetails
import com.example.product_details.domain.api.repository.ProductDetailsRepository
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(ProductDetailsScope::class)
@ContributesBinding(ProductDetailsScope::class)
@Inject
internal class ProductDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val productDetailsMapper: ProductDetailsMapper
) : ProductDetailsRepository {
    override suspend fun getProductDetails(
        id: Long
    ): ProductDetails {
        return productDetailsMapper mapToDomain remoteDataSource.getProductInfo(
            id,
            listOf(
                Selector.TITLE, Selector.PRICE, Selector.DESCRIPTION, Selector.WEIGHT,
                Selector.RATING, Selector.AVAILABILITY_STATUS, Selector.WARRANTY_INFORMATION
            )
        )
    }
}