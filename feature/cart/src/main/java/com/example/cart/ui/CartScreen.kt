package com.example.cart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cart.di.createCartGraph
import com.example.common.ui.Loading
import com.example.common.ui.LocalAppComponent

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onCartItemClick: (id: Long) -> Unit
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createCartGraph() }
    val factory = remember(graph) { graph.getCartViewModelFactory() }
    val viewModel = viewModel<CartViewModel>(factory = factory)
    val cart = viewModel.states.collectAsStateWithLifecycle()
    val currentCart = cart.value
    val snackbar = remember { SnackbarHostState() }

    ObserveEffects(
        effects = viewModel.effects,
        onProductClick = onCartItemClick,
        onBackClick = onBackClick
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                isNotEmpty = currentCart.cart.isNotEmpty(),
                onBackClick = {
                    viewModel.accept(CartEvent.Ui.BackClicked)
                },
                onClearCartClick = {
                    viewModel.accept(CartEvent.Ui.ClearCart)
                },
                modifier = Modifier
            )
        },
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) { paddingValues ->
        when {
            currentCart.isLoading -> Loading(Modifier.padding(paddingValues))

            currentCart.cart.isEmpty() -> EmptyCart(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            else -> NonEmptyCart(
                cart = currentCart,
                onCartItemClick = { id ->
                    viewModel.accept(CartEvent.Ui.ProductClicked(id))
                },
                Modifier.padding(paddingValues)
            )
        }
    }
}