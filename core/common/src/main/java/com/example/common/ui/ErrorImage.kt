package com.example.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorImage(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Filled.Warning,
        contentDescription = null,
        modifier = modifier,
        tint = Color.Red
    )
}