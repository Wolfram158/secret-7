package com.example.product_list.ui

internal sealed interface ProductListScreenEvent {
    class Loading(val pages: String) : ProductListScreenEvent
    class Error(val pages: String) : ProductListScreenEvent
    class Success(val countOfLoadedItems: Int) : ProductListScreenEvent
}