package com.example.product_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Suppress("UNCHECKED_CAST")
@SingleIn(ProductDetailsScope::class)
@Inject
internal class ProductDetailsViewModelFactory(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val incrementCartElementCountUseCase: IncrementCartElementCountUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(
            getProductDetailsUseCase = getProductDetailsUseCase,
            incrementCartElementCountUseCase = incrementCartElementCountUseCase
        ) as T
    }
}