package com.example.product_list.domain.impl

import com.example.product_list.di.ProductListScope
import com.example.product_list.domain.api.repository.ProductListRepository
import com.example.product_list.domain.api.usecase.GetProductsUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(ProductListScope::class)
@ContributesBinding(ProductListScope::class)
@Inject
internal class GetProductsUseCaseImpl(
    private val repository: ProductListRepository
) : GetProductsUseCase {
    override suspend fun invoke(limit: Int, skip: Int) = repository.getProducts(
        limit = limit,
        skip = skip
    )
}