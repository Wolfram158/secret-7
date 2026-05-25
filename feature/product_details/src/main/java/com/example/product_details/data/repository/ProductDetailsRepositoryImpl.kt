package com.example.product_details.data.repository

import com.example.database.api.LocalDataSource
import com.example.network.api.RemoteDataSource
import com.example.network.api.Selector
import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.model.ProductDetails
import com.example.product_details.domain.api.repository.ProductDetailsRepository
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@SingleIn(ProductDetailsScope::class)
@ContributesBinding(ProductDetailsScope::class)
@Inject
internal class ProductDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val productDetailsMapper: ProductDetailsMapper
) : ProductDetailsRepository {
    override suspend fun getProductDetails(
        id: Long
    ): ProductDetails {
        val local = localDataSource.getProductDetailsDbo(id)
        if (local != null &&
            System.currentTimeMillis() - local.updatedAt <= MAX_DELTA_MILLIS
        ) {
            return productDetailsMapper.mapToDomain(local, true)
        }
        val remoteResult = runCatching {
            remoteDataSource.getProductInfo(
                id,
                listOf(
                    Selector.TITLE, Selector.PRICE, Selector.DESCRIPTION, Selector.WEIGHT,
                    Selector.RATING, Selector.AVAILABILITY_STATUS, Selector.WARRANTY_INFORMATION,
                    Selector.THUMBNAIL, Selector.ID
                )
            )
        }
        return when (val remote = remoteResult.getOrNull()) {
            null -> {
                when (local) {
                    null -> {
                        productDetailsMapper
                            .mapToDomain(remoteResult.getOrThrow(), false)
                    }

                    else -> {
                        productDetailsMapper
                            .mapToDomain(
                                local,
                                false
                            )
                    }
                }
            }

            else -> {
                localDataSource.insertProductDetailsDbo(
                    productDetailsMapper.mapRemoteToLocal(id, remote)
                )
                productDetailsMapper.mapToDomain(remote, true)
            }
        }
    }

    companion object {
        const val MAX_DELTA_HOURS = 24
        private val MAX_DELTA_MILLIS =
            MAX_DELTA_HOURS.toDuration(DurationUnit.HOURS).inWholeMilliseconds
    }
}