package com.example.settings.api

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val remindAboutPurchase: Boolean
) {
    companion object {
        inline val DEFAULT
            get() = Settings(
                remindAboutPurchase = false
            )
    }
}
