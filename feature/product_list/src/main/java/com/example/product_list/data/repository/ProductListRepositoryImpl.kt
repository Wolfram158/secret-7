package com.example.product_list.data.repository

import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector
import com.example.product_list.di.ProductListScope
import com.example.product_list.domain.api.model.ShortProductInfo
import com.example.product_list.domain.api.repository.ProductListRepository
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(ProductListScope::class)
@ContributesBinding(ProductListScope::class)
@Inject
internal class ProductListRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val productListMapper: ProductListMapper
) : ProductListRepository {
    override suspend fun getProducts(
        limit: Int,
        skip: Int
    ): List<ShortProductInfo> {
        return productListMapper mapToDomain remoteDataSource.getProducts(
            limit = limit,
            skip = skip,
            select = listOf(
                Selector.TITLE, Selector.PRICE, Selector.BRAND
            )
        )
    }
}