package com.example.product_list.ui

import com.example.product_list.domain.api.model.ShortProductInfo

internal data class ProductListState(
    val products: List<ShortProductInfo> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasError: Boolean = false,
    val lastLoadedPage: Int = -1
) {
    companion object {
        val initial: ProductListState
            get() = ProductListState()
    }
}

internal sealed interface ProductListEvent {
    sealed interface Ui : ProductListEvent {
        object Init : Ui
        object Retry : Ui
        data class ProductClicked(val id: Long) : Ui
        data class LoadMore(val lastVisibleIndex: Int) : Ui
        object CartClicked : Ui
    }

    sealed interface Internal : ProductListEvent {
        data class InitialProductsLoaded(val products: List<ShortProductInfo>) : Internal
        data class MoreProductsLoaded(val products: List<ShortProductInfo>) : Internal
        object LoadError : Internal
        object MoreLoadError : Internal
    }
}

internal sealed interface ProductListCommand {
    object LoadInitial : ProductListCommand
    data class LoadMore(val skip: Int) : ProductListCommand
}

internal sealed interface ProductListEffect {
    data class ShowLoading(val pages: String) : ProductListEffect
    data class ShowError(val pages: String) : ProductListEffect
    data class ShowSuccess(val countOfLoadedItems: Int) : ProductListEffect
    data class NavigateToProduct(val id: Long) : ProductListEffect
    object NavigateToCart : ProductListEffect
}