package com.example.cart.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
    val cart = viewModel.cart.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                isNotEmpty = cart.value is CartState.NonEmpty,
                onBackClick = onBackClick,
                onClearCartClick = viewModel::clearCart,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        when (val cart = cart.value) {
            CartState.Empty -> EmptyCart(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            CartState.Loading -> Loading(Modifier.padding(paddingValues))
            is CartState.NonEmpty -> NonEmptyCart(
                cart = cart,
                onCartItemClick = onCartItemClick,
                Modifier.padding(paddingValues)
            )
        }
    }
}