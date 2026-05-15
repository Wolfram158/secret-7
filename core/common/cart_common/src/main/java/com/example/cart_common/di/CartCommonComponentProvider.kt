package com.example.cart_common.di

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.di.AppComponent

object CartCommonComponentProvider {
    fun provideCartCommonComponent(appComponent: AppComponent): CartCommonComponent {
        val graph = appComponent.createCartCommonGraph()
        return object : CartCommonComponent {
            override val clearCartUseCase: Lazy<ClearCartUseCase> = graph.clearCartUseCase
            override val getCartUseCase: Lazy<GetCartUseCase> = graph.getCartUseCase
            override val incrementCartElementCountUseCase: Lazy<IncrementCartElementCountUseCase> =
                graph.incrementCartElementCountByIdUseCase
        }
    }
}