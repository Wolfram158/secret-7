package com.example.cart.ui

import com.example.cart_common.domain.api.model.CartElement

internal sealed interface CartState {
    object Loading : CartState
    object Empty : CartState
    class NonEmpty(val cart: List<CartElement>) : CartState
}