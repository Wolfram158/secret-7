package com.example.cart_common.domain.api.usecase

import com.example.cart_common.domain.api.model.CartElement

interface GetCartUseCase {
    suspend fun invoke(): List<CartElement>
}