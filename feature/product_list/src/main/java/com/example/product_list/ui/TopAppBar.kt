package com.example.product_list.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.common.ui.NetworkState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBar(
    onGotoCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOnline = remember { mutableStateOf(false) }

    NetworkState {
        isOnline.value = it
    }

    TopAppBar(
        {
            when (isOnline.value) {
                true -> Text("Online", style = MaterialTheme.typography.bodyMedium)
                else -> Text("Offline", style = MaterialTheme.typography.bodyMedium)
            }
        },
        actions = {
            IconButton(
                onClick = onGotoCart
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = modifier
                )
            }
        }
    )
}