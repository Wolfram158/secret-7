package com.example.cart_common.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.common.getAppComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        with(context.getAppComponent()) {
            val pendingResult = goAsync()
            defaultScope.launch {
                try {
                    if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
                        return@launch
                    }
                    val enabled = settingsDataSource.getRemindAboutPurchaseFlow().first()
                    if (!enabled) {
                        return@launch
                    }
                    AlarmScheduler.scheduleReminder(context)
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}