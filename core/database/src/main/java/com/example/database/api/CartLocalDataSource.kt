package com.example.database.api

interface CartLocalDataSource {
    suspend fun getCart(): List<CartElementDbo>

    suspend fun incrementCartElementCount(id: Long, title: String)

    suspend fun clearCart()
}