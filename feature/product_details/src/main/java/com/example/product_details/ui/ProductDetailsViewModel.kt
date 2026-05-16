package com.example.product_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val incrementCartElementCountUseCase: IncrementCartElementCountUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private val _productDetails = MutableStateFlow<ProductDetailsState>(ProductDetailsState.Loading)
    val productDetails = _productDetails.asStateFlow()

    fun getProductDetails(id: Long) {
        viewModelScope.launch(defaultDispatcher) {
            try {
                _productDetails.update {
                    ProductDetailsState.Loading
                }
                _productDetails.update {
                    ProductDetailsState.Success(
                        getProductDetailsUseCase(id)
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _productDetails.update {
                    ProductDetailsState.Error
                }
            }
        }
    }

    fun incrementCartElementCount() {
        viewModelScope.launch(defaultDispatcher) {
            when (val productDetails = _productDetails.value) {
                is ProductDetailsState.Success -> incrementCartElementCountUseCase(
                    productDetails.productDetails.id,
                    productDetails.productDetails.title
                )

                else -> {}
            }
        }
    }
}