package com.example.cart_common.domain.impl.usecase

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.model.CartElement
import com.example.cart_common.domain.api.repository.CartRepository
import com.example.cart_common.domain.api.usecase.GetCartFlowUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow

@Inject
@SingleIn(CartCommonScope::class)
@ContributesBinding(CartCommonScope::class)
internal class GetCartFlowUseCaseImpl(
    private val repository: CartRepository
) : GetCartFlowUseCase {
    override operator fun invoke(): Flow<List<CartElement>> = repository.getCartFlow()
}