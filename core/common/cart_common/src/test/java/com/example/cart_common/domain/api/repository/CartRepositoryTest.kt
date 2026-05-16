package com.example.cart_common.domain.api.repository

import androidx.test.core.app.ApplicationProvider
import com.example.cart_common.di.CartCommonGraph
import com.example.cart_common.di.createCartCommonGraph
import com.example.cart_common.domain.api.model.CartElement
import com.example.di.AppComponent
import com.example.di.AppComponentProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertContains
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class CartRepositoryTest {
    private lateinit var appComponent: AppComponent
    private lateinit var graph: CartCommonGraph
    private lateinit var repository: CartRepository

    private suspend fun addSeveralItems(count: Int) {
        repeat(count) {
            repository.incrementCartElementCount(it.toLong(), it.toString())
        }
    }

    private suspend fun getCart() = repository.getCartFlow().first()

    @Before
    fun setUp() = runBlocking {
        appComponent =
            AppComponentProvider.provideAppComponent(ApplicationProvider.getApplicationContext())
        graph = appComponent.createCartCommonGraph()
        repository = graph.getCartRepository()
    }

    @After
    fun clear() {
        appComponent.localDataSource.close()
    }

    @Test
    fun `WHEN add product at the first time and then increment THEN product added once, no duplicates`() =
        runTest {
            val title = ""
            repository.incrementCartElementCount(1, title)
            repository.incrementCartElementCount(1, title)
            val cart = getCart()
            assertEquals(1, cart.size)
            assertEquals(2, cart.first().count)
        }

    @Test
    fun `WHEN add product at the first time THEN product added`() = runTest {
        val title = ""
        val element = CartElement(1, title, 1)
        assertTrue {
            !getCart().contains(element)
        }
        repository.incrementCartElementCount(1, title)
        assertContains(repository.getCartFlow().first(), element)
    }

    @Test
    fun `WHEN add several different items THEN all are saved`() =
        runTest {
            val count = 5
            addSeveralItems(count)
            val cart = getCart()
            assertEquals(count, cart.size)
        }

    @Test
    fun `WHEN clear cart THEN cart is cleared`() =
        runTest {
            val count = 10
            addSeveralItems(count)
            repository.clearCart()
            val cart = getCart()
            assertEquals(0, cart.size)
        }
}