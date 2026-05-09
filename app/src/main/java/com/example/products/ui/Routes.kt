package com.example.products.ui

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object ProductList

    @Serializable
    data class ProductDetails(val id: Long)
}