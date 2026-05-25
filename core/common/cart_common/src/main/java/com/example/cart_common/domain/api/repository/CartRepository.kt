package com.example.cart_common.domain.api.repository

import com.example.cart_common.domain.api.model.CartElement
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun incrementCartElementCount(id: Long, title: String)
    suspend fun getCart(): List<CartElement>
    fun getCartFlow(): Flow<List<CartElement>>
    suspend fun clearCart()
}