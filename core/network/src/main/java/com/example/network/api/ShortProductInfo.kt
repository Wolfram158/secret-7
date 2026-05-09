package com.example.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortProductInfo(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("price") val price: Double,
    @SerialName("brand") val brand: String? = null
)
