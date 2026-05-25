package com.example.cart.ui

import money.vivid.elmslie.core.store.ScreenReducer
import money.vivid.elmslie.core.store.StateReducer

internal class CartReducer : ScreenReducer<
        CartEvent,
        CartEvent.Ui,
        CartEvent.Internal,
        CartState,
        CartEffect,
        CartCommand
        >(CartEvent.Ui::class, CartEvent.Internal::class) {
    override fun StateReducer<CartEvent, CartState, CartEffect, CartCommand>.Result.internal(
        event: CartEvent.Internal
    ) {
        when (event) {
            is CartEvent.Internal.Loaded -> {
                state {
                    copy(
                        cart = event.cart,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun StateReducer<CartEvent, CartState, CartEffect, CartCommand>.Result.ui(
        event: CartEvent.Ui
    ) {
        when (event) {
            CartEvent.Ui.BackClicked -> {
                effects {
                    +CartEffect.NavigateToBack
                }
            }

            CartEvent.Ui.ClearCart -> {
                commands {
                    +CartCommand.ClearCartAndThenLoad
                }
            }

            CartEvent.Ui.Load -> {
                state {
                    copy(isLoading = true)
                }
                commands {
                    +CartCommand.Load
                }
            }

            is CartEvent.Ui.ProductClicked -> {
                effects {
                    +CartEffect.NavigateToProduct(event.id)
                }
            }
        }
    }
}