package com.example.cart_common.domain.api.usecase

interface IncrementCartElementCountUseCase {
    suspend operator fun invoke(id: Long, title: String)
}