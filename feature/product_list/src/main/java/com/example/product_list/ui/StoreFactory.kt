package com.example.product_list.ui

import com.example.product_list.domain.api.usecase.GetProductsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import money.vivid.elmslie.core.store.ElmStore
import money.vivid.elmslie.core.store.Store

internal fun createProductListStore(
    getProductsUseCase: GetProductsUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Store<ProductListEvent, ProductListEffect, ProductListState> {
    return ElmStore(
        initialState = ProductListState.initial,
        reducer = ProductListReducer(),
        actor = ProductListActor(getProductsUseCase, dispatcher),
        startEvent = ProductListEvent.Ui.Init
    )
}