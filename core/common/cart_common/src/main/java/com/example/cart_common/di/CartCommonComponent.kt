package com.example.cart_common.di

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase

interface CartCommonComponent {
    val clearCartUseCase: Lazy<ClearCartUseCase>
    val getCartFlowUseCase: Lazy<GetCartFlowUseCase>
    val incrementCartElementCountUseCase: Lazy<IncrementCartElementCountUseCase>
}