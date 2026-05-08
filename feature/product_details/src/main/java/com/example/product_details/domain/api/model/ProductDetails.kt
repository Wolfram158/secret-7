package com.example.product_details.domain.api.model

internal data class ProductDetails(
    val title: String,
    val description: String,
    val rating: Double,
    val price: Double,
    val weight: Double,
    val availabilityStatus: String,
    val warrantyInformation: String
)