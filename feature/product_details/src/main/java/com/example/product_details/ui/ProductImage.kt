package com.example.product_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.common.ui.ErrorImage
import com.example.common.ui.Loading

@Composable
internal fun ProductImage(
    url: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier.clip(RectangleShape),
        contentScale = ContentScale.Crop,
        loading = {
            Loading()
        },
        error = {
            ErrorImage()
        }
    )
}