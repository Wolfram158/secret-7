package com.example.cart.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ObserveEffects(
    effects: Flow<CartEffect>,
    onProductClick: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                CartEffect.NavigateToBack -> {
                    onBackClick()
                }

                is CartEffect.NavigateToProduct -> {
                    onProductClick(effect.id)
                }
            }
        }
    }
}