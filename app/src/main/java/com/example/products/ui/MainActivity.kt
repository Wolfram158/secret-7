package com.example.products.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.common.getAppComponent
import com.example.common.ui.LocalAppComponent
import com.example.common.ui.theme.ProductsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProductsTheme {
                CompositionLocalProvider(LocalAppComponent provides getAppComponent()) {
                    val navHostController = rememberNavController()
                    NavGraph(navHostController)
                }
            }
        }
    }
}