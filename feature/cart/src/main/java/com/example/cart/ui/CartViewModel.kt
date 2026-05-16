package com.example.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class CartViewModel(
    private val clearCartUseCase: ClearCartUseCase,
    getCartFlowUseCase: GetCartFlowUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    val cart = getCartFlowUseCase()
        .map { cart ->
            if (cart.isEmpty()) {
                CartState.Empty
            } else {
                CartState.NonEmpty(cart)
            }
        }
        .stateIn(
            viewModelScope, SharingStarted.Lazily, CartState.Loading
        )

    fun clearCart() {
        viewModelScope.launch(defaultDispatcher) {
            clearCartUseCase()
        }
    }

}