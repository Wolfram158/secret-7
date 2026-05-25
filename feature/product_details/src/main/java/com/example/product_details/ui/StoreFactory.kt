package com.example.product_details.ui

import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import money.vivid.elmslie.core.store.ElmStore
import money.vivid.elmslie.core.store.Store

internal fun createProductDetailsStore(
    id: Long,
    getProductDetailsUseCase: GetProductDetailsUseCase,
    incrementCartElementCountUseCase: IncrementCartElementCountUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Store<ProductDetailsEvent, ProductDetailsEffect, ProductDetailsState> {
    return ElmStore(
        initialState = ProductDetailsState.initial,
        reducer = ProductDetailsReducer(),
        actor = ProductDetailsActor(
            getProductDetailsUseCase,
            incrementCartElementCountUseCase,
            dispatcher
        ),
        startEvent = ProductDetailsEvent.Ui.Load(id)
    )
}