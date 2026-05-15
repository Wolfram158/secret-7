package com.example.product_details.di

import com.example.cart_common.di.CartCommonComponentProvider
import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.database.api.LocalDataSource
import com.example.di.AppComponent
import com.example.network.api.RemoteDataSource
import com.example.product_details.ui.ProductDetailsViewModelFactory
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

@DependencyGraph(ProductDetailsScope::class, [CartCommonScope::class])
internal interface ProductDetailsGraph {
    fun getProductDetailsViewModelFactory(): ProductDetailsViewModelFactory

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides remoteDataSource: RemoteDataSource,
            @Provides localDataSource: LocalDataSource,
            @Provides incrementCartElementCountUseCase: IncrementCartElementCountUseCase
        ): ProductDetailsGraph
    }
}

internal fun AppComponent.createProductDetailsGraph(): ProductDetailsGraph {
    val cartCommonComponent = CartCommonComponentProvider.provideCartCommonComponent(this)
    return createGraphFactory<ProductDetailsGraph.Factory>().create(
        remoteDataSource,
        localDataSource,
        cartCommonComponent.incrementCartElementCountUseCase.value
    )
}