package com.example.product_list.ui

import com.example.product_list.domain.api.model.ShortProductInfo
import com.example.product_list.ui.ProductListCommand.LoadMore
import com.example.product_list.ui.ProductListEffect.NavigateToCart
import com.example.product_list.ui.ProductListEffect.NavigateToProduct
import money.vivid.elmslie.core.store.ScreenReducer
import money.vivid.elmslie.core.store.StateReducer

internal class ProductListReducer : ScreenReducer<
        ProductListEvent,
        ProductListEvent.Ui,
        ProductListEvent.Internal,
        ProductListState,
        ProductListEffect,
        ProductListCommand
        >(ProductListEvent.Ui::class, ProductListEvent.Internal::class) {
    override fun StateReducer<ProductListEvent, ProductListState, ProductListEffect, ProductListCommand>.Result.internal(
        event: ProductListEvent.Internal
    ) {
        when (event) {
            is ProductListEvent.Internal.InitialProductsLoaded -> {
                state {
                    copy(
                        products = event.products,
                        isInitialLoading = false,
                        hasError = false,
                        lastLoadedPage = event.products.size.lastLoadedPage
                    )
                }
            }

            ProductListEvent.Internal.LoadError -> {
                state {
                    copy(isInitialLoading = false, hasError = true)
                }
            }

            ProductListEvent.Internal.MoreLoadError -> {
                state {
                    copy(isLoadingMore = false)
                }
            }

            is ProductListEvent.Internal.MoreProductsLoaded -> {
                val newProducts = mergeProducts(state.products, event.products)
                state {
                    copy(
                        products = newProducts,
                        isLoadingMore = false,
                        lastLoadedPage = newProducts.size.lastLoadedPage
                    )
                }
            }
        }
    }

    override fun StateReducer<ProductListEvent, ProductListState, ProductListEffect, ProductListCommand>.Result.ui(
        event: ProductListEvent.Ui
    ) {
        when (event) {
            ProductListEvent.Ui.Init -> {
                state {
                    copy(isInitialLoading = true, hasError = false)
                }
                commands {
                    +ProductListCommand.LoadInitial
                }
            }

            is ProductListEvent.Ui.LoadMore -> {
                val remained = state.lastLoadedPage * ON_PAGE - event.lastVisibleIndex
                if (remained > REMAINED_LIMIT || state.isLoadingMore) {
                    return
                }
                state {
                    copy(isLoadingMore = true)
                }
                commands {
                    +LoadMore(state.lastLoadedPage * ON_PAGE)
                }
            }

            is ProductListEvent.Ui.ProductClicked -> {
                effects {
                    +NavigateToProduct(event.id)
                }
            }

            ProductListEvent.Ui.Retry -> {
                state {
                    copy(isInitialLoading = true, hasError = false)
                }
                commands {
                    +ProductListCommand.LoadInitial
                }
            }

            ProductListEvent.Ui.CartClicked -> {
                effects {
                    +NavigateToCart
                }
            }
        }
    }

    private val Int.lastLoadedPage
        get() = div(ON_PAGE)

    private fun mergeProducts(
        old: List<ShortProductInfo>,
        addition: List<ShortProductInfo>
    ): List<ShortProductInfo> {
        return buildList {
            addAll(old)
            loop@ for (new in addition) {
                for (i in maxOf(0, size - ON_PAGE * INITIAL_PAGES_COUNT)..<size) {
                    if (get(i).id == new.id) {
                        continue@loop
                    }
                }
                add(new)
            }
        }
    }

    companion object {
        const val ON_PAGE = 10
        const val INITIAL_PAGES_COUNT = 2
        const val REMAINED_LIMIT = 5
    }
}