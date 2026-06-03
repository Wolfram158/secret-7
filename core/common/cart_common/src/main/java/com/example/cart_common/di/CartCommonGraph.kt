package com.example.cart_common.di

import com.example.cart_common.domain.api.repository.CartRepository
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.database.api.LocalDataSource
import com.example.di.AppComponent
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.CoroutineScope

@DependencyGraph(CartCommonScope::class)
internal interface CartCommonGraph {
    val clearCartUseCase: Lazy<ClearCartUseCase>
    val getCartFlowUseCase: Lazy<GetCartFlowUseCase>
    val incrementCartElementCountByIdUseCase: Lazy<IncrementCartElementCountUseCase>
    val getCartUseCase: Lazy<GetCartUseCase>

    fun getCartRepository(): CartRepository

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides localDataSource: LocalDataSource,
            @Provides defaultScope: CoroutineScope
        ): CartCommonGraph
    }
}

internal fun AppComponent.createCartCommonGraph() =
    createGraphFactory<CartCommonGraph.Factory>().create(
        localDataSource,
        defaultScope
    )