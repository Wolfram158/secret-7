package com.example.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cart.di.CartScope
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Suppress("UNCHECKED_CAST")
@SingleIn(CartScope::class)
@Inject
internal class CartViewModelFactory(
    private val clearCartUseCase: ClearCartUseCase,
    private val getCartUseCase: GetCartUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(
            clearCartUseCase = clearCartUseCase,
            getCartUseCase = getCartUseCase
        ) as T
    }
}