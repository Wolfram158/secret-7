package com.example.cart_common.domain.impl.usecase

import com.example.cart_common.di.CartCommonScope
import com.example.cart_common.domain.api.repository.CartRepository
import com.example.cart_common.domain.api.usecase.IncrementCartElementCountUseCase
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(CartCommonScope::class)
@ContributesBinding(CartCommonScope::class)
internal class IncrementCartElementCountByIdUseCaseImpl(
    private val repository: CartRepository
) : IncrementCartElementCountUseCase {
    override suspend operator fun invoke(id: Long, title: String) =
        repository.incrementCartElementCount(id, title)
}