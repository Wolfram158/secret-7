package com.example.product_list.ui

import com.example.product_list.domain.api.model.ShortProductInfo

internal sealed interface ProductListState {
    object Loading : ProductListState

    object Error : ProductListState

    class Success(val products: List<ShortProductInfo>) : ProductListState
}