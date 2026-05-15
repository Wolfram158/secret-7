package com.example.cart_common.domain.impl.usecase

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.repository.CartRepository
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(CartCommonScope::class)
@ContributesBinding(CartCommonScope::class)
internal class ClearCartUseCaseImpl(
    private val repository: CartRepository
) : ClearCartUseCase {
    override suspend fun invoke() = repository.clearCart()
}