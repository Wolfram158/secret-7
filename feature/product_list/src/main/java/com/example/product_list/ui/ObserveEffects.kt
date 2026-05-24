package com.example.product_list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
internal fun ObserveEffects(
    events: Flow<ProductListEffect>,
    snackbar: SnackbarHostState,
    onProductClick: (Long) -> Unit,
    onGotoCart: () -> Unit
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ProductListEffect.NavigateToProduct -> {
                    onProductClick(event.id)
                }

                is ProductListEffect.ShowError -> {
                    launch {
                        snackbar.showSnackbar("Error occurred when loading pages ${event.pages}")
                    }
                }

                is ProductListEffect.ShowLoading -> {
                    launch {
                        snackbar.showSnackbar("Loading pages ${event.pages}...")
                    }
                }

                is ProductListEffect.ShowSuccess -> {
                    launch {
                        snackbar.showSnackbar("Successfully loaded ${event.countOfLoadedItems} new items")
                    }
                }

                ProductListEffect.NavigateToCart -> {
                    onGotoCart()
                }
            }
        }
    }
}