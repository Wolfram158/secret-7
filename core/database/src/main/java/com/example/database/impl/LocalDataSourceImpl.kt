package com.example.database.impl

import com.example.database.api.CartElementDbo
import com.example.database.api.LocalDataSource
import com.example.database.api.ProductDetailsDbo
import kotlinx.coroutines.flow.Flow

internal class LocalDataSourceImpl(
    private val productDetailsDao: ProductDetailsDao,
    private val cartDao: CartDao,
    private val onClose: () -> Unit
) : LocalDataSource {
    override suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo? {
        return productDetailsDao.getProductDetailsDbo(id)
    }

    override suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo) {
        return productDetailsDao.insertProductDetailsDbo(productDetailsDbo)
    }

    override fun getCartFlow(): Flow<List<CartElementDbo>> {
        return cartDao.getCartFlow()
    }

    override suspend fun getCart(): List<CartElementDbo> {
        return cartDao.getCart()
    }

    override suspend fun incrementCartElementCount(id: Long, title: String) {
        cartDao.incrementCartElementCount(id, title)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override fun close() = onClose()
}