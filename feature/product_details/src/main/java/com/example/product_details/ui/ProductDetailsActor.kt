package com.example.product_details.ui

import android.util.Log
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import money.vivid.elmslie.core.store.Actor

internal class ProductDetailsActor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val incrementCartElementCountUseCase: IncrementCartElementCountUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Actor<ProductDetailsCommand, ProductDetailsEvent.Internal>() {
    override fun execute(command: ProductDetailsCommand): Flow<ProductDetailsEvent.Internal> =
        flow {
            when (command) {
                is ProductDetailsCommand.IncrementCount -> {
                    incrementCartElementCountUseCase(
                        command.productDetails.id,
                        command.productDetails.title
                    )
                }

                is ProductDetailsCommand.Load -> {
                    try {
                        emit(
                            ProductDetailsEvent.Internal.ProductDetailsLoaded(
                                getProductDetailsUseCase(command.id)
                            )
                        )
                    } catch (_: Exception) {
                        emit(ProductDetailsEvent.Internal.LoadError)
                    }
                }
            }
        }
            .flowOn(dispatcher)
}