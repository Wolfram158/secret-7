package com.example.product_details.di

import com.example.di.AppComponent
import com.example.network.api.RemoteDataSource
import com.example.product_details.ui.ProductDetailsViewModelFactory
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

@DependencyGraph(ProductDetailsScope::class)
internal interface ProductDetailsGraph {
    fun getProductDetailsViewModelFactory(): ProductDetailsViewModelFactory

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides remoteDataSource: RemoteDataSource,
        ): ProductDetailsGraph
    }
}

internal fun AppComponent.createProductDetailsGraph() =
    createGraphFactory<ProductDetailsGraph.Factory>().create(remoteDataSource)