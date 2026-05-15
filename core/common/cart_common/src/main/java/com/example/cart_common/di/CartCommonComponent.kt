package com.example.cart_common.di

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase

interface CartCommonComponent {
    val clearCartUseCase: Lazy<ClearCartUseCase>
    val getCartUseCase: Lazy<GetCartUseCase>
    val incrementCartElementCountUseCase: Lazy<IncrementCartElementCountUseCase>
}