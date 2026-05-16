package com.example.cart.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBar(
    isNotEmpty: Boolean,
    onBackClick: () -> Unit,
    onClearCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        {

        },
        actions = {
            if (isNotEmpty) {
                IconButton(
                    onClick = onClearCartClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = modifier
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = modifier
                )
            }
        })
}