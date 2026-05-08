package com.example.product_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.ui.Error
import com.example.common.ui.Loading
import com.example.common.ui.LocalAppComponent
import com.example.product_details.di.createProductDetailsGraph

@Composable
fun ProductDetailsScreen(
    id: Long,
    modifier: Modifier = Modifier
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createProductDetailsGraph() }
    val factory = remember(graph) { graph.getProductDetailsViewModelFactory() }
    val viewModel = viewModel<ProductDetailsViewModel>(factory = factory)
    val productDetails = viewModel.productDetails.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getProductDetails(id)
    }

    when (val state = productDetails.value) {
        ProductDetailsState.Error -> Error(
            errorText = "Couldn't load product details",
            onRetry = { viewModel.getProductDetails(id) },
            modifier = modifier
        )

        ProductDetailsState.Loading -> Loading(modifier)
        is ProductDetailsState.Success -> ProductDetailsSuccessScreen(state, modifier)
    }
}