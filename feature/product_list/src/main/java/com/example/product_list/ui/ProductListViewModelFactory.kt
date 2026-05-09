package com.example.product_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.product_list.di.ProductListScope
import com.example.product_list.domain.api.usecase.GetProductsUseCase
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Suppress("UNCHECKED_CAST")
@SingleIn(ProductListScope::class)
@Inject
internal class ProductListViewModelFactory(
    private val getProductDetailsUseCase: GetProductsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductListViewModel(
            getProductsUseCase = getProductDetailsUseCase
        ) as T
    }
}