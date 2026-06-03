package com.example.product_list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ObserveEffects(
    effects: Flow<ProductListEffect>,
    onProductClick: (Long) -> Unit,
    onGotoCart: () -> Unit
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                is ProductListEffect.NavigateToProduct -> {
                    onProductClick(effect.id)
                }

                ProductListEffect.NavigateToCart -> {
                    onGotoCart()
                }
            }
        }
    }
}