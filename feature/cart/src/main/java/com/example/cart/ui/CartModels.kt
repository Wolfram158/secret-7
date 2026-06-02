package com.example.cart.ui

import com.example.cart_common.domain.api.model.CartElement
import com.example.settings.api.Settings

internal data class CartState(
    val cart: List<CartElement> = emptyList(),
    val isLoading: Boolean = false,
    val remindAboutPurchase: Boolean = false
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
        object GetRemindAboutPurchase : Ui
        object ChangeRemindAboutPurchase : Ui
    }

    sealed interface Internal : CartEvent {
        data class CartLoaded(val cart: List<CartElement>) : Internal
        data class SettingsLoaded(val settings: Settings) : Internal
    }
}

internal sealed interface CartCommand {
    object LoadCart : CartCommand
    object ClearCartAndThenLoad : CartCommand
    object LoadSettings : CartCommand
    object ChangeRemindAboutPurchase : CartCommand
}

internal sealed interface CartEffect {
    data class NavigateToProduct(val id: Long) : CartEffect
    object NavigateToBack : CartEffect
}