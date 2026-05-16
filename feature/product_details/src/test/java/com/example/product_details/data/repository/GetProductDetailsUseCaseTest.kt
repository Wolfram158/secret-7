package com.example.product_details.data.repository

import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.database.api.LocalDataSource
import com.example.network.api.ProductInfo
import com.example.network.api.RemoteDataSource
import com.example.product_details.di.ProductDetailsGraph
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetProductDetailsUseCaseTest {
    private val remoteDataSource = mock<RemoteDataSource>()
    private val localDataSource = mock<LocalDataSource>()
    private val incrementCartElementCountUseCase = mock<IncrementCartElementCountUseCase>()
    private val graph = createGraphFactory<ProductDetailsGraph.Factory>()
        .create(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            incrementCartElementCountUseCase = incrementCartElementCountUseCase
        )
    private val productDetailsMapper = graph.getProductDetailsMapper()
    private val getProductDetailsUseCase = graph.getGetProductDetailsUseCase()
    private val productInfo = ProductInfo(
        id = 1,
        thumbnail = "",
        title = "",
        description = "",
        rating = 2.71,
        price = 1.16,
        weight = 3.2,
        availabilityStatus = "",
        warrantyInformation = ""
    )
    private val dbo = productDetailsMapper.mapRemoteToLocal(1, productInfo)
    private val oldDbo =
        dbo.copy(
            updatedAt = dbo.updatedAt - (ProductDetailsRepositoryImpl.MAX_DELTA_HOURS + 1).toDuration(
                DurationUnit.HOURS
            ).inWholeMilliseconds
        )

    @Test
    fun `WHEN cache is actual THEN no calling remote data source`() = runTest {
        whenever(localDataSource.getProductDetailsDbo(1)).thenReturn(dbo)
        getProductDetailsUseCase(1)
        verify(remoteDataSource, never()).getProductInfo(any(), any())
    }

    @Test
    fun `WHEN cache is not actual THEN calling remote data source`() = runTest {
        whenever(localDataSource.getProductDetailsDbo(1)).thenReturn(oldDbo)
        getProductDetailsUseCase(1)
        verify(remoteDataSource, times(1)).getProductInfo(any(), any())
    }

    @Test
    fun `WHEN no cache THEN calling remote data source`() = runTest {
        whenever(localDataSource.getProductDetailsDbo(1)).thenReturn(null)
        whenever(remoteDataSource.getProductInfo(any(), any())).thenReturn(productInfo)
        getProductDetailsUseCase(1)
        verify(remoteDataSource, times(1)).getProductInfo(any(), any())
    }

    @Test
    fun `WHEN cache is not actual and calling remote data source failed THEN cache is returned`() =
        runTest {
            whenever(localDataSource.getProductDetailsDbo(1)).thenReturn(oldDbo)
            whenever(remoteDataSource.getProductInfo(any(), any())).thenThrow(
                RuntimeException("Connection error")
            )
            val result = getProductDetailsUseCase(1)
            assertEquals(
                productDetailsMapper.mapToDomain(oldDbo, false),
                result
            )
        }

    @Test
    fun `WHEN no cache and calling remote data source failed THEN exception thrown`() =
        runTest {
            whenever(localDataSource.getProductDetailsDbo(1)).thenReturn(null)
            whenever(remoteDataSource.getProductInfo(any(), any())).thenThrow(
                RuntimeException("Connection error")
            )
            assertFailsWith<Exception> {
                getProductDetailsUseCase(1)
            }
        }
}