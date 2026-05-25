package com.example.product_details.ui

import com.example.product_details.ui.ProductDetailsCommand.IncrementCount
import com.example.product_details.ui.ProductDetailsCommand.Load
import money.vivid.elmslie.core.store.ScreenReducer
import money.vivid.elmslie.core.store.StateReducer

internal class ProductDetailsReducer : ScreenReducer<
        ProductDetailsEvent,
        ProductDetailsEvent.Ui,
        ProductDetailsEvent.Internal,
        ProductDetailsState,
        ProductDetailsEffect,
        ProductDetailsCommand
        >(ProductDetailsEvent.Ui::class, ProductDetailsEvent.Internal::class) {
    override fun StateReducer<ProductDetailsEvent, ProductDetailsState, ProductDetailsEffect, ProductDetailsCommand>.Result.internal(
        event: ProductDetailsEvent.Internal
    ) {
        when (event) {
            ProductDetailsEvent.Internal.LoadError -> {
                state {
                    ProductDetailsState.Error
                }
            }

            is ProductDetailsEvent.Internal.ProductDetailsLoaded -> {
                state {
                    ProductDetailsState.Success(event.productDetails)
                }
            }
        }
    }

    override fun StateReducer<ProductDetailsEvent, ProductDetailsState, ProductDetailsEffect, ProductDetailsCommand>.Result.ui(
        event: ProductDetailsEvent.Ui
    ) {
        when (event) {
            ProductDetailsEvent.Ui.CartClicked -> {
                effects {
                    +ProductDetailsEffect.NavigateToCart
                }
            }

            is ProductDetailsEvent.Ui.IncrementCount -> {
                commands {
                    +IncrementCount(event.productDetails)
                }
            }

            is ProductDetailsEvent.Ui.Load -> {
                commands {
                    +Load(event.id)
                }
            }

            is ProductDetailsEvent.Ui.Retry -> {
                state {
                    ProductDetailsState.Loading
                }
                commands {
                    +Load(event.id)
                }
            }

            ProductDetailsEvent.Ui.BackClicked -> {
                effects {
                    +ProductDetailsEffect.NavigateToBack
                }
            }
        }
    }
}