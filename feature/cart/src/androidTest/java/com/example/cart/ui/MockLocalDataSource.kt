package com.example.cart.ui

import com.example.database.api.CartElementDbo
import com.example.database.api.LocalDataSource
import com.example.database.api.ProductDetailsDbo
import kotlinx.coroutines.flow.Flow

internal class MockLocalDataSource(
    private val getCartResult: List<CartElementDbo> = listOf()
) : LocalDataSource {
    override fun getCartFlow(): Flow<List<CartElementDbo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): List<CartElementDbo> = getCartResult

    override suspend fun incrementCartElementCount(id: Long, title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo? {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}