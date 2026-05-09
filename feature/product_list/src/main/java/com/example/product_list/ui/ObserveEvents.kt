package com.example.product_list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
internal fun ObserveEvents(
    events: Flow<ProductListScreenEvent>,
    snackbar: SnackbarHostState
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ProductListScreenEvent.Error ->
                    snackbar.showSnackbar("Error occurred when loading pages ${event.pages}")

                is ProductListScreenEvent.Loading ->
                    snackbar.showSnackbar("Loading pages ${event.pages}...")

                is ProductListScreenEvent.Success ->
                    snackbar.showSnackbar("Successfully loaded ${event.countOfLoadedItems} new items")
            }
        }
    }
}