package com.example.common.ui

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {
    const val DEEP_LINK_SCHEME = "productsstore"
    const val CART_DEEP_LINK = "$DEEP_LINK_SCHEME://cart"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val permissions = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )
}