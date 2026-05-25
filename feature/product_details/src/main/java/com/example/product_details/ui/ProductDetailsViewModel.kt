package com.example.product_details.ui

import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.common.ui.ElmStoreViewModel
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class ProductDetailsViewModel(
    private val id: Long,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val incrementCartElementCountUseCase: IncrementCartElementCountUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ElmStoreViewModel<ProductDetailsEvent, ProductDetailsEffect, ProductDetailsState>(
    ProductDetailsState.initial,
    {
        createProductDetailsStore(
            id,
            getProductDetailsUseCase,
            incrementCartElementCountUseCase,
            dispatcher
        )
    }
)