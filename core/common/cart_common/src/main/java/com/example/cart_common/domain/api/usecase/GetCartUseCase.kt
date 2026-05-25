package com.example.cart_common.domain.api.usecase

import com.example.cart_common.domain.api.model.CartElement

interface GetCartUseCase {
    suspend operator fun invoke(): List<CartElement>
}