package com.example.product_details.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.ui.Error
import com.example.common.ui.Loading
import com.example.common.ui.LocalAppComponent
import com.example.product_details.di.createProductDetailsGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    id: Long,
    onBackClick: () -> Unit,
    onGotoCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createProductDetailsGraph() }
    val factory = remember(graph) { graph.getProductDetailsViewModelFactoryFactory().create(id) }
    val viewModel = viewModel<ProductDetailsViewModel>(factory = factory)
    val productDetails = viewModel.states.collectAsStateWithLifecycle()
    val currentProductDetails = productDetails.value

    ObserveEffects(
        effects = viewModel.effects,
        onBackClick = onBackClick,
        onGotoCart = onGotoCart
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                onBackClick = {
                    viewModel.accept(ProductDetailsEvent.Ui.BackClicked)
                },
                onIncrementCartElementCount = {
                    if (currentProductDetails is ProductDetailsState.Success) {
                        viewModel.accept(
                            ProductDetailsEvent.Ui.IncrementCount(
                                currentProductDetails.productDetails
                            )
                        )
                    }
                },
                onGotoCart = {
                    viewModel.accept(ProductDetailsEvent.Ui.CartClicked)
                },
                modifier = Modifier.size(24.dp)
            )
        }
    ) { paddingValues ->
        when (currentProductDetails) {
            ProductDetailsState.Error -> Error(
                errorText = "Couldn't load product details",
                onRetry = { viewModel.accept(ProductDetailsEvent.Ui.Retry(id)) },
                modifier = Modifier.padding(paddingValues)
            )

            ProductDetailsState.Loading -> Loading(Modifier.padding(paddingValues))
            is ProductDetailsState.Success -> ProductDetailsSuccessScreen(
                currentProductDetails,
                Modifier.padding(paddingValues)
            )
        }
    }
}