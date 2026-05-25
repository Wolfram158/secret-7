package com.example.product_list.ui

import com.example.common.ui.ElmStoreViewModel
import com.example.product_list.domain.api.usecase.GetProductsUseCase

internal class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ElmStoreViewModel<ProductListEvent, ProductListEffect, ProductListState>(
    ProductListState.initial,
    { createProductListStore(getProductsUseCase) })