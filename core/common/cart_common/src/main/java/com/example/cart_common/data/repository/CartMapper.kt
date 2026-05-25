package com.example.cart_common.data.repository

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.model.CartElement
import com.example.database.api.CartElementDbo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
@SingleIn(CartCommonScope::class)
internal class CartMapper {
    fun mapLocalToDomain(cartElementDbo: CartElementDbo) = with(cartElementDbo) {
        CartElement(
            id = id,
            title = title,
            count = count
        )
    }

    fun mapLocalsToDomains(locals: Flow<List<CartElementDbo>>) = locals.map { cart ->
        cart.map { mapLocalToDomain(it) }
    }

    fun mapLocalsToDomains(locals: List<CartElementDbo>) = locals.map { cartElementDbo ->
        mapLocalToDomain(cartElementDbo)
    }
}