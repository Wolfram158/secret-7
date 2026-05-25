package com.example.cart.ui

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.common.ui.ElmStoreViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class CartViewModel(
    private val clearCartUseCase: ClearCartUseCase,
    getCartUseCase: GetCartUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ElmStoreViewModel<CartEvent, CartEffect, CartState>(
    CartState.initial,
    { createCartStore(getCartUseCase, clearCartUseCase, defaultDispatcher) })