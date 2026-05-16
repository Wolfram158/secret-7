package com.example.cart.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cart_common.domain.api.model.CartElement

@Composable
internal fun CartItem(
    cartElement: CartElement,
    onCartItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(vertical = 4.dp)
            .clickable {
                onCartItemClick(cartElement.id)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            cartElement.title,
            Modifier
                .padding(start = 16.dp)
                .padding(vertical = 24.dp)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.width(8.dp))
        Text(
            cartElement.count.toString(),
            Modifier
                .padding(end = 16.dp)
                .padding(vertical = 24.dp),
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}