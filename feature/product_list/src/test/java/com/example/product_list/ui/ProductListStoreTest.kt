package com.example.product_list.ui

import com.example.product_list.domain.api.model.ShortProductInfo
import com.example.product_list.domain.api.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import money.vivid.elmslie.core.config.ElmslieConfig
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListStoreTest {
    private val getProductUseCase = mock<GetProductsUseCase>()
    private val testDispatcher = StandardTestDispatcher()
    private val products = listOf(
        ShortProductInfo(1, "Yes", 1.0),
        ShortProductInfo(2, "Hello", 2.0)
    )

    @Before
    fun beforeEach() {
        ElmslieConfig.elmDispatcher { testDispatcher }
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN error on initial loading THEN state contains information about error`() =
        runTest {
            whenever(getProductUseCase(any(), any())).thenThrow(
                RuntimeException("Connection error")
            )
            val store = createProductListStore(getProductUseCase, testDispatcher)
            store.start()
            advanceUntilIdle()
            assertTrue(store.states.value.hasError)
        }

    @Test
    fun `WHEN success on initial loading THEN state contains information about success`() =
        runTest {
            whenever(getProductUseCase(any(), any()))
                .thenReturn(products)
            val store = createProductListStore(getProductUseCase, testDispatcher)
            assertTrue(store.states.value.products.isEmpty())
            store.start()
            advanceUntilIdle()
            assertFalse(store.states.value.hasError)
            assertEquals(products, store.states.value.products)
        }

    @Test
    fun `WHEN navigate to details THEN navigation effect is emitted`() =
        runTest {
            val store = createProductListStore(getProductUseCase, testDispatcher)
            val effects = mutableListOf<ProductListEffect>()
            val effectsJob = launch { store.effects.toList(effects) }
            store.start()
            val id = 3L
            store.accept(ProductListEvent.Ui.ProductClicked(id))
            advanceUntilIdle()
            assertEquals(ProductListEffect.NavigateToProduct(id), effects.last())
            effectsJob.cancel()
        }

    @Test
    fun `WHEN navigate to cart THEN navigation effect is emitted`() =
        runTest {
            val store = createProductListStore(getProductUseCase, testDispatcher)
            val effects = mutableListOf<ProductListEffect>()
            val effectsJob = launch { store.effects.toList(effects) }
            store.start()
            store.accept(ProductListEvent.Ui.CartClicked)
            advanceUntilIdle()
            assertEquals(ProductListEffect.NavigateToCart, effects.last())
            effectsJob.cancel()
        }
}