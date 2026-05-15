package com.example.cart_common.domain.api.repository

import com.example.cart_common.domain.api.model.CartElement

interface CartRepository {
    suspend fun incrementCartElementCount(id: Long, title: String)
    suspend fun getCart(): List<CartElement>
    suspend fun clearCart()
}