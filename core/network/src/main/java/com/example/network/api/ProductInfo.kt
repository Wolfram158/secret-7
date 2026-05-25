package com.example.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductInfo(
    @SerialName("id") val id: Long,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("rating") val rating: Double,
    @SerialName("price") val price: Double,
    @SerialName("weight") val weight: Double,
    @SerialName("availabilityStatus") val availabilityStatus: String,
    @SerialName("warrantyInformation") val warrantyInformation: String
)
