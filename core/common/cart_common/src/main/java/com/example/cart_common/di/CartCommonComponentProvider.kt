package com.example.cart_common.di

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.di.AppComponent

object CartCommonComponentProvider {
    fun provideCartCommonComponent(appComponent: AppComponent): CartCommonComponent {
        val graph = appComponent.createCartCommonGraph()
        return object : CartCommonComponent {
            override val clearCartUseCase: Lazy<ClearCartUseCase> = graph.clearCartUseCase
            override val getCartFlowUseCase: Lazy<GetCartFlowUseCase> = graph.getCartFlowUseCase
            override val incrementCartElementCountUseCase: Lazy<IncrementCartElementCountUseCase> =
                graph.incrementCartElementCountByIdUseCase
        }
    }
}