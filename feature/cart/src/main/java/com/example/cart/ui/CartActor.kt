package com.example.cart.ui

import com.example.cart.ui.CartEvent.Internal.CartLoaded
import com.example.cart.ui.CartEvent.Internal.SettingsLoaded
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import com.example.settings.api.SettingsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import money.vivid.elmslie.core.store.Actor

internal class CartActor(
    private val clearCartUseCase: ClearCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val settingsDataSource: SettingsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Actor<CartCommand, CartEvent.Internal>() {
    override fun execute(command: CartCommand): Flow<CartEvent.Internal> = flow {
        when (command) {
            CartCommand.ClearCartAndThenLoad -> {
                clearCartUseCase()
                emit(CartLoaded(getCartUseCase()))
            }

            CartCommand.LoadCart -> {
                emit(CartLoaded(getCartUseCase()))
            }

            CartCommand.LoadSettings -> {
                settingsDataSource.getSettingsFlow().collect {
                    emit(SettingsLoaded(it))
                }
            }

            CartCommand.ChangeRemindAboutPurchase -> {
                settingsDataSource.changeRemindAboutPurchase()
            }
        }
    }
        .flowOn(dispatcher)
}