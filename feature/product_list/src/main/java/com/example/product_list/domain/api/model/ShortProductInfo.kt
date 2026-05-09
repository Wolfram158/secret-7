package com.example.product_list.domain.api.model

internal data class ShortProductInfo(
    val id: Long,
    val title: String,
    val price: Double,
    val brand: String? = null
)
