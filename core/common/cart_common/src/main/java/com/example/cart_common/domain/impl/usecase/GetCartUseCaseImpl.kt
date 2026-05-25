package com.example.cart_common.domain.impl.usecase

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.model.CartElement
import com.example.cart_common.domain.api.repository.CartRepository
import com.example.cart_common.domain.api.usecase.GetCartUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(CartCommonScope::class)
@ContributesBinding(CartCommonScope::class)
internal class GetCartUseCaseImpl(
    private val repository: CartRepository
) : GetCartUseCase {
    override suspend operator fun invoke(): List<CartElement> = repository.getCart()
}