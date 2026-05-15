package com.example.database.impl

import com.example.database.api.CartElementDbo
import com.example.database.api.LocalDataSource
import com.example.database.api.ProductDetailsDbo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@BindingContainer
@Inject
internal class LocalDataSourceImpl(
    private val productDetailsDao: ProductDetailsDao,
    private val cartDao: CartDao
) : LocalDataSource {
    override suspend fun getProductDetailsDbo(id: Long): ProductDetailsDbo? {
        return productDetailsDao.getProductDetailsDbo(id)
    }

    override suspend fun insertProductDetailsDbo(productDetailsDbo: ProductDetailsDbo) {
        return productDetailsDao.insertProductDetailsDbo(productDetailsDbo)
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
}