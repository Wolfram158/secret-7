package com.example.cart.ui

import com.example.cart_common.domain.api.model.CartElement
import com.example.cart_common.domain.api.usecase.ClearCartUseCase
import com.example.cart_common.domain.api.usecase.GetCartUseCase
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
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CartStoreTest {
    private val getCartUseCase = mock<GetCartUseCase>()
    private val clearCartUseCase = mock<ClearCartUseCase>()
    private val testDispatcher = StandardTestDispatcher()
    private val cart = listOf(
        CartElement(1, "Hello", 3),
        CartElement(2, "Yes", 4)
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
    fun `WHEN success THEN state model contains correct cart`() =
        runTest {
            whenever(getCartUseCase())
                .thenReturn(cart)
            val store = createCartStore(getCartUseCase, clearCartUseCase, testDispatcher)
            assertTrue(store.states.value.cart.isEmpty())
            store.start()
            advanceUntilIdle()
            assertEquals(cart, store.states.value.cart)
        }

    @Test
    fun `WHEN navigate to details THEN navigation effect is emitted`() =
        runTest {
            val store = createCartStore(getCartUseCase, clearCartUseCase, testDispatcher)
            val effects = mutableListOf<CartEffect>()
            val effectsJob = launch { store.effects.toList(effects) }
            store.start()
            val id = 3L
            store.accept(CartEvent.Ui.ProductClicked(id))
            advanceUntilIdle()
            assertEquals(CartEffect.NavigateToProduct(id), effects.last())
            effectsJob.cancel()
        }

    @Test
    fun `WHEN navigate to back THEN navigation effect is emitted`() =
        runTest {
            val store = createCartStore(getCartUseCase, clearCartUseCase, testDispatcher)
            val effects = mutableListOf<CartEffect>()
            val effectsJob = launch { store.effects.toList(effects) }
            store.start()
            store.accept(CartEvent.Ui.BackClicked)
            advanceUntilIdle()
            assertEquals(CartEffect.NavigateToBack, effects.last())
            effectsJob.cancel()
        }
}