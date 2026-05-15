package com.example.cart_common.data.repository

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.model.CartElement
import com.example.cart_common.domain.api.repository.CartRepository
import com.example.database.api.LocalDataSource
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(CartCommonScope::class)
@ContributesBinding(CartCommonScope::class)
internal class CartCommonRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val cartMapper: CartMapper
) : CartRepository {
    override suspend fun incrementCartElementCount(id: Long, title: String) {
        localDataSource.incrementCartElementCount(id, title)
    }

    override suspend fun getCart(): List<CartElement> {
        return cartMapper.mapLocalsToDomains(localDataSource.getCart())
    }

    override suspend fun clearCart() {
        localDataSource.clearCart()
    }
}