package com.example.cart.ui

import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import money.vivid.elmslie.core.store.Actor

internal class CartActor(
    private val clearCartUseCase: ClearCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Actor<CartCommand, CartEvent.Internal>() {
    override fun execute(command: CartCommand): Flow<CartEvent.Internal> = flow {
        when (command) {
            CartCommand.ClearCartAndThenLoad -> {
                clearCartUseCase()
                emit(CartEvent.Internal.Loaded(getCartUseCase()))
            }

            CartCommand.Load -> {
                emit(CartEvent.Internal.Loaded(getCartUseCase()))
            }
        }
    }
        .flowOn(dispatcher)
}