package com.example.cart_common.data.repository

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.model.CartElement
import com.example.database.api.CartElementDbo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

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

    fun mapLocalsToDomains(locals: Iterable<CartElementDbo>) = locals.map {
        mapLocalToDomain(it)
    }
}