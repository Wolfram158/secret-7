package com.example.product_details.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.common.ui.LocalAppComponent
import com.example.common.ui.TestMainActivity
import com.example.common.ui.TestTags
import com.example.common.ui.theme.ProductsTheme
import com.example.database.api.LocalDataSource
import com.example.database.api.MockLocalDataSource
import com.example.di.AppComponent
import com.example.network.api.MockRemoteDataSource
import com.example.network.api.ProductInfo
import com.example.network.api.RemoteDataSource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
internal class ProductDetailsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestMainActivity>()

    private fun getProductInfo(id: Long) = ProductInfo(
        id = id,
        thumbnail = "",
        title = "com.example.common.ui.TestTags",
        description = "description123",
        rating = 2.3,
        price = 5.3,
        weight = 2.29,
        availabilityStatus = "",
        warrantyInformation = ""
    )

    @Test
    fun whenErrorFirstlyAndSuccessSecondlyThenProductDetailsAppearOnTryAgain() {
        with(composeTestRule) {
            val remoteDataSourceImpl = MockRemoteDataSource()
            val id = 3L
            val productInfo = getProductInfo(id)
            remoteDataSourceImpl.getProductInfo = {
                throw RuntimeException("Connection error")
            }

            val appComponent = object : AppComponent {
                override val remoteDataSource: RemoteDataSource = remoteDataSourceImpl
                override val localDataSource: LocalDataSource = MockLocalDataSource()
            }

            setContent {
                ProductsTheme {
                    CompositionLocalProvider(LocalAppComponent provides appComponent) {
                        ProductDetailsScreen(id, {}, {})
                    }
                }
            }

            waitForIdle()

            waitUntilExactlyOneExists(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    TestTags.TRY_AGAIN_BUTTON
                ),
                3000,
            )
            remoteDataSourceImpl.getProductInfo = { productInfo }
            onNodeWithTag(TestTags.TRY_AGAIN_BUTTON).performClick()
            waitUntilExactlyOneExists(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    TestTags.PRODUCT_DETAILS_DESCRIPTION
                ),
                3000,
            )
            onNodeWithTag(TestTags.TRY_AGAIN_BUTTON).assertDoesNotExist()
        }
    }
}