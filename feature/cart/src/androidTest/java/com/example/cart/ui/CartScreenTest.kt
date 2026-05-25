package com.example.cart.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.common.ui.LocalAppComponent
import com.example.common.ui.TestMainActivity
import com.example.common.ui.TestTags
import com.example.common.ui.theme.ProductsTheme
import com.example.database.api.CartElementDbo
import com.example.database.api.LocalDataSource
import com.example.database.api.MockLocalDataSource
import com.example.di.AppComponent
import com.example.network.api.MockRemoteDataSource
import com.example.network.api.RemoteDataSource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
internal class CartScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestMainActivity>()

    private val cart = listOf(
        CartElementDbo(id = 1, title = "Yes", count = 3),
        CartElementDbo(id = 2, title = "Hello", count = 4)
    )

    @Test
    fun whenCartIsEmptyThenClearCartButtonDoesNotAppear() {
        with(composeTestRule) {
            val appComponent = object : AppComponent {
                override val remoteDataSource: RemoteDataSource = MockRemoteDataSource()
                override val localDataSource: LocalDataSource = MockLocalDataSource()
            }

            setContent {
                ProductsTheme {
                    CompositionLocalProvider(LocalAppComponent provides appComponent) {
                        CartScreen({}) { }
                    }
                }
            }

            waitForIdle()
            onNodeWithTag(TestTags.CLEAR_CART_BUTTON).assertDoesNotExist()
        }
    }

    @Test
    fun whenCartIsNotEmptyThenClearCartButtonAppears() {
        with(composeTestRule) {
            val appComponent = object : AppComponent {
                override val remoteDataSource: RemoteDataSource = MockRemoteDataSource()
                override val localDataSource: LocalDataSource = MockLocalDataSource(
                    getCartResult = cart
                )
            }

            setContent {
                ProductsTheme {
                    CompositionLocalProvider(LocalAppComponent provides appComponent) {
                        CartScreen({}) { }
                    }
                }
            }

            waitForIdle()
            onNodeWithTag(TestTags.CLEAR_CART_BUTTON).assertExists()
        }
    }

    @Test
    fun whenCartIsNotEmptyThenCountOfElementsIsCorrect() {
        with(composeTestRule) {
            val appComponent = object : AppComponent {
                override val remoteDataSource: RemoteDataSource = MockRemoteDataSource()
                override val localDataSource: LocalDataSource = MockLocalDataSource(
                    getCartResult = cart
                )
            }

            setContent {
                ProductsTheme {
                    CompositionLocalProvider(LocalAppComponent provides appComponent) {
                        CartScreen({}) { }
                    }
                }
            }

            waitForIdle()
            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    TestTags.CART_ELEMENT
                ),
                2,
                3000
            )
        }
    }
}