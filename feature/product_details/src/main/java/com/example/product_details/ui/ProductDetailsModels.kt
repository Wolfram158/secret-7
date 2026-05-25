package com.example.product_details.ui

import com.example.product_details.domain.api.model.ProductDetails

internal sealed interface ProductDetailsState {
    object Loading : ProductDetailsState

    object Error : ProductDetailsState

    data class Success(val productDetails: ProductDetails) : ProductDetailsState

    companion object {
        inline val initial: ProductDetailsState
            get() = Loading
    }
}

internal sealed interface ProductDetailsEvent {
    sealed interface Ui : ProductDetailsEvent {
        data class Load(val id: Long) : Ui
        data class Retry(val id: Long) : Ui
        object CartClicked : Ui
        object BackClicked : Ui
        data class IncrementCount(val productDetails: ProductDetails) : Ui
    }

    sealed interface Internal : ProductDetailsEvent {
        data class ProductDetailsLoaded(val productDetails: ProductDetails) : Internal
        object LoadError : Internal
    }
}

internal sealed interface ProductDetailsCommand {
    data class Load(val id: Long) : ProductDetailsCommand
    data class IncrementCount(val productDetails: ProductDetails) : ProductDetailsCommand
}

internal sealed interface ProductDetailsEffect {
    object NavigateToCart : ProductDetailsEffect
    object NavigateToBack : ProductDetailsEffect
}