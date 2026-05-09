package com.example.product_list.di

import com.example.di.AppComponent
import com.example.network.api.RemoteDataSource
import com.example.product_list.ui.ProductListViewModelFactory
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

@DependencyGraph(ProductListScope::class)
internal interface ProductListGraph {
    fun getProductListViewModelFactory(): ProductListViewModelFactory

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides remoteDataSource: RemoteDataSource
        ): ProductListGraph
    }
}

internal fun AppComponent.createProductListGraph() =
    createGraphFactory<ProductListGraph.Factory>().create(remoteDataSource)