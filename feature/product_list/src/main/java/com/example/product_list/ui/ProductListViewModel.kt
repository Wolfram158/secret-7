package com.example.product_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_list.domain.api.model.ShortProductInfo
import com.example.product_list.domain.api.usecase.GetProductsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private val _products = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val products = _products.asStateFlow()
    private var lastLoadedPage = -1
    private val mtx = Mutex()
    private val _events = Channel<ProductListScreenEvent>(Channel.CONFLATED)
    val events = _events.receiveAsFlow()

    init {
        doInitialLoading()
    }

    fun doInitialLoading() {
        viewModelScope.launch(defaultDispatcher) {
            mtx.withLock {
                try {
                    _products.update {
                        ProductListState.Loading
                    }
                    val products = getProductsUseCase(
                        ITEMS_PER_LOAD,
                        0
                    )
                    _products.update {
                        ProductListState.Success(products)
                    }
                    lastLoadedPage = products.size / ON_PAGE
                } catch (e: CancellationException) {
                    throw e
                } catch (_: Exception) {
                    _products.update {
                        ProductListState.Error
                    }
                }
            }
        }
    }

    fun getProducts(index: Int?) {
        if (index == null) {
            return
        }
        viewModelScope.launch(defaultDispatcher) {
            mtx.withLock {
                try {
                    val remained = lastLoadedPage * ON_PAGE - (index + 1)
                    if (remained > REMAINED_LIMIT) {
                        return@withLock
                    }
                    _events.send(
                        ProductListScreenEvent.Loading(
                            "${lastLoadedPage + 1}, ${lastLoadedPage + 2}"
                        )
                    )
                    val products = getProductsUseCase(
                        ITEMS_PER_LOAD,
                        lastLoadedPage * ON_PAGE
                    )
                    val oldItemsCount =
                        (_products.value as? ProductListState.Success)?.products?.size ?: 0
                    _products.update { state ->
                        when (state) {
                            ProductListState.Error -> ProductListState.Success(products)
                            ProductListState.Loading -> ProductListState.Loading
                            is ProductListState.Success -> ProductListState.Success(
                                mergeProducts(state.products, products)
                            )
                        }
                    }
                    lastLoadedPage += products.size / ON_PAGE
                    val totalItems = (_products.value as? ProductListState.Success)
                    if (totalItems != null) {
                        _events.send(
                            ProductListScreenEvent.Success(
                                totalItems.products.size - oldItemsCount
                            )
                        )
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (_: Exception) {
                    _events.send(
                        ProductListScreenEvent.Error(
                            "${lastLoadedPage + 1}, ${lastLoadedPage + 2}"
                        )
                    )
                }
            }
        }
    }

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
        private const val ON_PAGE = 10
        private const val INITIAL_PAGES_COUNT = 2
        private const val ITEMS_PER_LOAD = ON_PAGE * INITIAL_PAGES_COUNT
        private const val REMAINED_LIMIT = 5
    }
}