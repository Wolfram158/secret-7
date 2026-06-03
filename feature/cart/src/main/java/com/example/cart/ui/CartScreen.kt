package com.example.cart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cart.di.createCartGraph
import com.example.cart_common.ui.AlarmScheduler
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
    val context = LocalContext.current

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
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RemindAboutPurchaseSwitch(currentCart.remindAboutPurchase) {
                if (it) {
                    AlarmScheduler.scheduleReminder(context)
                } else {
                    AlarmScheduler.cancelReminder(context)
                }
                viewModel.accept(CartEvent.Ui.ChangeRemindAboutPurchase)
            }
            CartContent(currentCart) { id ->
                viewModel.accept(CartEvent.Ui.ProductClicked(id))
            }
        }
    }
}