package com.example.database.api

import kotlinx.coroutines.flow.Flow

class MockLocalDataSource(
    private val getCartResult: List<CartElementDbo> = listOf()
) : LocalDataSource {
    override fun getCartFlow(): Flow<List<CartElementDbo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): List<CartElementDbo> = getCartResult

    override suspend fun incrementCartElementCount(id: Long, title: String) {
    }

    override suspend fun clearCart() {
    }

    override suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo? {
        return null
    }

    override suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo) {
    }

    override fun close() {
    }

}