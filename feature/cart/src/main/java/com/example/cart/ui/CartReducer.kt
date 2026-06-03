package com.example.cart.ui

import com.example.cart.ui.CartEffect.NavigateToProduct
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
            is CartEvent.Internal.CartLoaded -> {
                state {
                    copy(
                        cart = event.cart,
                        isLoading = false
                    )
                }
            }

            is CartEvent.Internal.SettingsLoaded -> {
                state {
                    copy(
                        remindAboutPurchase = event.settings.remindAboutPurchase
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
                    +CartCommand.LoadCart
                }
            }

            is CartEvent.Ui.ProductClicked -> {
                effects {
                    +NavigateToProduct(event.id)
                }
            }

            CartEvent.Ui.ChangeRemindAboutPurchase -> {
                commands {
                    +CartCommand.ChangeRemindAboutPurchase
                }
            }

            CartEvent.Ui.GetRemindAboutPurchase -> {
                commands {
                    +CartCommand.LoadSettings
                }
            }
        }
    }
}