package com.example.cart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.ui.Loading

@Composable
internal fun CartContent(
    currentCart: CartState,
    onCartItemClick: (Long) -> Unit
) {
    when {
        currentCart.isLoading -> Loading()

        currentCart.cart.isEmpty() -> EmptyCart(
            Modifier
                .fillMaxSize()
        )

        else -> NonEmptyCart(
            cart = currentCart,
            onCartItemClick = onCartItemClick
        )
    }
}