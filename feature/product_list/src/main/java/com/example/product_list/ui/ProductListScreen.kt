package com.example.product_list.ui

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
import com.example.common.ui.Error
import com.example.common.ui.Loading
import com.example.common.ui.LocalAppComponent
import com.example.product_list.di.createProductListGraph

@Composable
fun ProductListScreen(
    onProductClick: (id: Long) -> Unit,
    onGotoCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createProductListGraph() }
    val factory = remember(graph) { graph.getProductListViewModelFactory() }
    val viewModel = viewModel<ProductListViewModel>(factory = factory)
    val products = viewModel.states.collectAsStateWithLifecycle()
    val currentState = products.value
    val snackbar = remember { SnackbarHostState() }

    ObserveEffects(
        viewModel.effects,
        snackbar,
        onProductClick,
        onGotoCart
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar({
                viewModel.accept(ProductListEvent.Ui.CartClicked)
            })
        },
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) { paddingValues ->
        when {
            currentState.isInitialLoading -> Loading(Modifier.padding(paddingValues))

            currentState.hasError -> Error(
                errorText = "Couldn't load products",
                onRetry = { viewModel.accept(ProductListEvent.Ui.Retry) },
                modifier = Modifier.padding(paddingValues)
            )

            else -> ProductListSuccessScreen(
                products = currentState.products,
                onProductClick = { id ->
                    viewModel.accept(ProductListEvent.Ui.ProductClicked(id))
                },
                onChangeLastVisibleItemIndex = { index ->
                    if (index != null) {
                        viewModel.accept(ProductListEvent.Ui.LoadMore(index))
                    }
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}