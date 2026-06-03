package com.example.product_list.data.repository

import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector
import com.example.product_list.di.ProductListScope
import com.example.product_list.domain.api.model.ShortProductInfo
import com.example.product_list.domain.api.repository.ProductListRepository
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@SingleIn(ProductListScope::class)
@ContributesBinding(ProductListScope::class)
@Inject
internal class ProductListRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val productListMapper: ProductListMapper
) : ProductListRepository {
    private val mtx = Mutex()
    private var skip: Int? = null
    private val select = listOf(
        Selector.TITLE, Selector.PRICE, Selector.BRAND
    ).joinToString(separator = ",") { it.selectorName }

    override suspend fun getProducts(
        limit: Int,
        skip: Int
    ): List<ShortProductInfo> {
        return mtx.withLock {
            val globalSkip = this.skip
            if (globalSkip != null && globalSkip >= skip) {
                return listOf()
            }
            productListMapper mapToDomain remoteDataSource.getProducts(
                limit = limit,
                skip = skip,
                select = select
            ).also {
                this.skip = skip
            }
        }
    }
}