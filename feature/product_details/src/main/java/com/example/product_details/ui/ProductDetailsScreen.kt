package com.example.product_details.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val appComponent = LocalAppComponent.current
    val graph = remember(appComponent) { appComponent.createProductDetailsGraph() }
    val factory = remember(graph) { graph.getProductDetailsViewModelFactory() }
    val viewModel = viewModel<ProductDetailsViewModel>(factory = factory)
    val productDetails = viewModel.productDetails.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getProductDetails(id)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                {

                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                })
        }
    ) { paddingValues ->
        when (val state = productDetails.value) {
            ProductDetailsState.Error -> Error(
                errorText = "Couldn't load product details",
                onRetry = { viewModel.getProductDetails(id) },
                modifier = Modifier.padding(paddingValues)
            )

            ProductDetailsState.Loading -> Loading(Modifier.padding(paddingValues))
            is ProductDetailsState.Success -> ProductDetailsSuccessScreen(
                state,
                modifier.padding(paddingValues)
            )
        }
    }
}