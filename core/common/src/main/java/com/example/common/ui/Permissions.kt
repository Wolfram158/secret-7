package com.example.common.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat

fun areAllGranted(
    context: Context,
    permissions: Array<String>
): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}

@Composable
fun RequestPermissions(
    context: Context,
    permissions: Array<String>,
    onGrantAllPermissions: () -> Unit
) {
    val areAllGranted = areAllGranted(context, permissions)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (areAllGranted(context, permissions)) {
            onGrantAllPermissions()
        }
    }
    LaunchedEffect(Unit) {
        if (!areAllGranted) {
            launcher.launch(permissions)
        }
    }
}