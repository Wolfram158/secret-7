package com.example.database.api

import kotlinx.coroutines.flow.Flow

interface CartLocalDataSource {
    fun getCartFlow(): Flow<List<CartElementDbo>>

    suspend fun incrementCartElementCount(id: Long, title: String)

    suspend fun clearCart()
}