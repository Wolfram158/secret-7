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
    modifier: Modifier = Modifier
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createProductListGraph() }
    val factory = remember(graph) { graph.getProductListViewModelFactory() }
    val viewModel = viewModel<ProductListViewModel>(factory = factory)
    val products = viewModel.products.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    ObserveEvents(
        viewModel.events,
        snackbar
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbar)
        }
    ) { paddingValues ->
        when (val state = products.value) {
            ProductListState.Error -> Error(
                errorText = "Couldn't load products",
                onRetry = viewModel::doInitialLoading,
                modifier = Modifier.padding(paddingValues)
            )

            ProductListState.Loading -> Loading(Modifier.padding(paddingValues))
            is ProductListState.Success -> ProductListSuccessScreen(
                state.products,
                onProductClick,
                viewModel::getProducts,
                Modifier.padding(paddingValues)
            )
        }
    }
}