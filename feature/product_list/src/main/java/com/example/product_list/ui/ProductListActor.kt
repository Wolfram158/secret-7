package com.example.product_list.ui

import com.example.product_list.domain.api.usecase.GetProductsUseCase
import com.example.product_list.ui.ProductListReducer.Companion.INITIAL_PAGES_COUNT
import com.example.product_list.ui.ProductListReducer.Companion.ON_PAGE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import money.vivid.elmslie.core.store.Actor

internal class ProductListActor(
    private val getProductsUseCase: GetProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Actor<ProductListCommand, ProductListEvent.Internal>() {
    override fun execute(command: ProductListCommand): Flow<ProductListEvent.Internal> = flow {
        try {
            when (command) {
                ProductListCommand.LoadInitial -> {
                    val products = getProductsUseCase(ITEMS_PER_LOAD, 0)
                    emit(ProductListEvent.Internal.InitialProductsLoaded(products))
                }

                is ProductListCommand.LoadMore -> {
                    val products = getProductsUseCase(ITEMS_PER_LOAD, command.skip)
                    emit(ProductListEvent.Internal.MoreProductsLoaded(products))
                }
            }
        } catch (_: Exception) {
            emit(
                if (command is ProductListCommand.LoadInitial) {
                    ProductListEvent.Internal.LoadError
                } else {
                    ProductListEvent.Internal.MoreLoadError
                }
            )
        }
    }
        .flowOn(dispatcher)

    companion object {
        private const val ITEMS_PER_LOAD = ON_PAGE * INITIAL_PAGES_COUNT
    }
}