package com.example.product_list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
internal fun ObserveEffects(
    effects: Flow<ProductListEffect>,
    snackbar: SnackbarHostState,
    onProductClick: (Long) -> Unit,
    onGotoCart: () -> Unit
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                is ProductListEffect.NavigateToProduct -> {
                    onProductClick(effect.id)
                }

                is ProductListEffect.ShowError -> {
                    launch {
                        snackbar.showSnackbar("Error occurred when loading pages ${effect.pages}")
                    }
                }

                is ProductListEffect.ShowLoading -> {
                    launch {
                        snackbar.showSnackbar("Loading pages ${effect.pages}...")
                    }
                }

                is ProductListEffect.ShowSuccess -> {
                    launch {
                        snackbar.showSnackbar("Successfully loaded ${effect.countOfLoadedItems} new items")
                    }
                }

                ProductListEffect.NavigateToCart -> {
                    onGotoCart()
                }
            }
        }
    }
}