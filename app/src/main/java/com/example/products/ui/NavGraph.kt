package com.example.products.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.cart.ui.CartScreen
import com.example.common.ui.Constants
import com.example.product_details.ui.ProductDetailsScreen
import com.example.product_list.ui.ProductListScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.ProductList
    ) {
        composable<Routes.ProductList> {
            ProductListScreen(
                onProductClick = { id ->
                    navHostController.navigate(Routes.ProductDetails(id)) {
                        launchSingleTop = true
                    }
                },
                onGotoCart = {
                    navHostController.navigate(Routes.Cart) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Routes.ProductDetails> { entry ->
            val route = entry.toRoute<Routes.ProductDetails>()
            ProductDetailsScreen(
                route.id,
                onBackClick = {
                    navHostController.popBackStack()
                },
                onGotoCart = {
                    navHostController.navigate(Routes.Cart) {
                        popUpTo(Routes.ProductList) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Routes.Cart>(
            deepLinks = listOf(
                navDeepLink { uriPattern = Constants.CART_DEEP_LINK }
            )
        ) {
            CartScreen(
                onBackClick = {
                    navHostController.popBackStack()
                },
                onCartItemClick = { id ->
                    navHostController.navigate(Routes.ProductDetails(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}