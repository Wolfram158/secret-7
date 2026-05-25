package com.example.cart.ui

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import money.vivid.elmslie.core.store.ElmStore
import money.vivid.elmslie.core.store.Store

internal fun createCartStore(
    getCartUseCase: GetCartUseCase,
    clearCartUseCase: ClearCartUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Store<CartEvent, CartEffect, CartState> {
    return ElmStore(
        initialState = CartState.initial,
        reducer = CartReducer(),
        actor = CartActor(clearCartUseCase, getCartUseCase, dispatcher),
        startEvent = CartEvent.Ui.Load
    )
}