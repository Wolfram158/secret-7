package com.example.products.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
                { id ->
                    navHostController.navigate(Routes.ProductDetails(id))
                }
            )
        }

        composable<Routes.ProductDetails> { entry ->
            val route = entry.toRoute<Routes.ProductDetails>()
            ProductDetailsScreen(
                route.id,
                {
                    navHostController.popBackStack()
                }
            )
        }
    }
}