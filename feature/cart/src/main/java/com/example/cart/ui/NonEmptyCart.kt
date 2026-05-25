package com.example.cart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.common.ui.TestTags

@Composable
internal fun NonEmptyCart(
    cart: CartState,
    onCartItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(cart.cart.size, { cart.cart[it].id }) { index ->
            CartItem(
                cartElement = cart.cart[index],
                onCartItemClick = onCartItemClick,
                modifier = Modifier.testTag(TestTags.CART_ELEMENT)
            )
        }
    }
}