package com.example.cart.ui

import com.example.cart_common.domain.api.model.CartElement

internal data class CartState(
    val cart: List<CartElement> = emptyList(),
    val isLoading: Boolean = false
) {
    companion object {
        inline val initial: CartState
            get() = CartState()
    }
}

internal sealed interface CartEvent {
    sealed interface Ui : CartEvent {
        data class ProductClicked(val id: Long) : Ui
        object BackClicked : Ui
        object Load : Ui
        object ClearCart : Ui
    }

    sealed interface Internal : CartEvent {
        data class Loaded(val cart: List<CartElement>) : Internal
    }
}

internal sealed interface CartCommand {
    object Load : CartCommand
    object ClearCartAndThenLoad : CartCommand
}

internal sealed interface CartEffect {
    data class NavigateToProduct(val id: Long) : CartEffect
    object NavigateToBack : CartEffect
}