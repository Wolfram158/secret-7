package com.example.product_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ProductDetailsSuccessScreen(
    productDetails: ProductDetailsState.Success,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Title(productDetails.productDetails.title)
        Spacer(Modifier.height(8.dp))
        ProductDetail(productDetails.productDetails.description)
        Spacer(Modifier.height(8.dp))
        ProductDetail("Rating: ${productDetails.productDetails.rating}")
        Spacer(Modifier.height(8.dp))
        ProductDetail("Weight: ${productDetails.productDetails.weight}")
        Spacer(Modifier.height(8.dp))
        ProductDetail("Price: ${productDetails.productDetails.price}")
        Spacer(Modifier.height(8.dp))
        ProductDetail(productDetails.productDetails.availabilityStatus)
        Spacer(Modifier.height(8.dp))
        ProductDetail(productDetails.productDetails.warrantyInformation)
    }
}