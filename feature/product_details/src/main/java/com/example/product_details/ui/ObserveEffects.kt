package com.example.product_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ObserveEffects(
    effects: Flow<ProductDetailsEffect>,
    onBackClick: () -> Unit,
    onGotoCart: () -> Unit
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                ProductDetailsEffect.NavigateToBack -> onBackClick()
                ProductDetailsEffect.NavigateToCart -> onGotoCart()
            }
        }
    }
}