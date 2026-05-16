package com.example.cart_common.domain.api.usecase

import com.example.cart_common.domain.api.model.CartElement
import kotlinx.coroutines.flow.Flow

interface GetCartFlowUseCase {
    operator fun invoke(): Flow<List<CartElement>>
}