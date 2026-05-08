package com.example.product_details.domain.impl.usecase

import com.example.product_details.di.ProductDetailsScope
import com.example.product_details.domain.api.model.ProductDetails
import com.example.product_details.domain.api.repository.ProductDetailsRepository
import com.example.product_details.domain.api.usecase.GetProductDetailsUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(ProductDetailsScope::class)
@ContributesBinding(ProductDetailsScope::class)
internal class GetProductDetailsUseCaseImpl(
    private val repository: ProductDetailsRepository
) : GetProductDetailsUseCase {
    override suspend operator fun invoke(
        id: Long
    ): ProductDetails = repository.getProductDetails(id)
}