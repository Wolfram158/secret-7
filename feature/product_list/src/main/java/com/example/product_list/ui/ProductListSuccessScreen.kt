package com.example.product_list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.example.common.ui.Loading
import com.example.product_list.domain.api.model.ShortProductInfo
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun ProductListSuccessScreen(
    products: List<ShortProductInfo>,
    isLoadingMore: Boolean,
    onProductClick: (id: Long) -> Unit,
    onChangeLastVisibleItemIndex: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()

    LaunchedEffect(state) {
        snapshotFlow {
            state.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { index ->
                onChangeLastVisibleItemIndex(index)
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = state
    ) {
        items(products, key = { it.id }) { item ->
            ProductListItem(item, onProductClick)
        }
        if (isLoadingMore) {
            item {
                Loading()
            }
        }
    }
}