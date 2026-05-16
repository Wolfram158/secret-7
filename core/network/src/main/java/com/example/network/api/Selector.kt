package com.example.network.api

enum class Selector {
    ID,
    TITLE,
    PRICE,
    BRAND,
    DESCRIPTION,
    RATING,
    WEIGHT,
    AVAILABILITY_STATUS,
    WARRANTY_INFORMATION,
    THUMBNAIL;

    override fun toString(): String = when (this) {
        AVAILABILITY_STATUS -> "availabilityStatus"
        WARRANTY_INFORMATION -> "warrantyInformation"
        else -> name.lowercase()
    }
}