package com.example.products.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.common.getAppComponent
import com.example.common.ui.Constants
import com.example.common.ui.LocalAppComponent
import com.example.common.ui.RequestPermissions
import com.example.common.ui.areAllGranted
import com.example.common.ui.theme.ProductsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProductsTheme {
                Root()
            }
        }
    }
}

@Composable
fun Context.Root() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val areAllGranted =
            remember { mutableStateOf(areAllGranted(context, Constants.permissions)) }
        if (!areAllGranted.value) {
            RequestPermissions(
                context = context,
                permissions = Constants.permissions,
                onGrantAllPermissions = { areAllGranted.value = true }
            )
        } else {
            Content()
        }
    } else {
        Content()
    }
}

@Composable
fun Context.Content() {
    CompositionLocalProvider(LocalAppComponent provides getAppComponent()) {
        val navHostController = rememberNavController()
        NavGraph(navHostController)
    }
}