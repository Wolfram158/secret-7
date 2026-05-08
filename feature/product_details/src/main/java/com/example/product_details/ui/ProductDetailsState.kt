package com.example.product_details.ui

import com.example.product_details.domain.api.model.ProductDetails

internal sealed interface ProductDetailsState {
    object Loading : ProductDetailsState

    object Error : ProductDetailsState

    class Success(val productDetails: ProductDetails) : ProductDetailsState
}