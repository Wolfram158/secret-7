package com.example.cart.di

import com.example.cart.ui.CartViewModelFactory
import com.example.cart_common.di.CartCommonComponentProvider
import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.di.AppComponent
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory

@DependencyGraph(CartScope::class, [CartCommonScope::class])
internal interface CartGraph {
    fun getCartViewModelFactory(): CartViewModelFactory

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides clearCartUseCase: ClearCartUseCase,
            @Provides getCartFlowUseCase: GetCartFlowUseCase,
            @Provides getCartUseCase: GetCartUseCase
        ): CartGraph
    }
}

internal fun AppComponent.createCartGraph(): CartGraph {
    val cartCommonComponent = CartCommonComponentProvider.provideCartCommonComponent(this)
    return createGraphFactory<CartGraph.Factory>().create(
        cartCommonComponent.clearCartUseCase.value,
        cartCommonComponent.getCartFlowUseCase.value,
        cartCommonComponent.getCartUseCase.value
    )
}