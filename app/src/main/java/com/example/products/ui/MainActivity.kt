package com.example.products.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.common.getAppComponent
import com.example.common.ui.LocalAppComponent
import com.example.common.ui.theme.ProductsTheme
import com.example.product_details.ui.ProductDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProductsTheme {
                CompositionLocalProvider(LocalAppComponent provides getAppComponent()) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        ProductDetailsScreen(1, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}