package com.example.cart_common.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.cart_common.R
import com.example.cart_common.di.CartCommonComponentProvider
import com.example.cart_common.domain.api.model.CartElement
import com.example.common.getAppComponent
import com.example.common.ui.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        with(context.getAppComponent()) {
            defaultScope.launch {
                val getCartUseCase =
                    CartCommonComponentProvider.provideCartCommonComponent(this@with)
                        .getCartUseCase
                val enabled = settingsDataSource.getRemindAboutPurchaseFlow().first()
                if (!enabled) {
                    return@launch
                }
                val cart = getCartUseCase.value()
                if (cart.isEmpty()) {
                    return@launch
                }
                createNotificationChannel(context)
                showNotification(context, cart)
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun showNotification(
        context: Context,
        items: List<CartElement>,
    ) {
        val cartIntent =
            Intent(Intent.ACTION_VIEW, Constants.CART_DEEP_LINK.toUri()).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val cartPendingIntent = PendingIntent.getActivity(
            context,
            0,
            cartIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentIntent = PendingIntent.getActivity(
            context,
            1,
            cartIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentText = items.joinToString(System.lineSeparator()) { "${it.title}: ${it.count}" }
        val action: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "Goto cart",
            cartPendingIntent
        ).build()
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(NAME)
            .setContentText(contentText)
            .setContentIntent(contentIntent)
            .addAction(
                action
            )
            .setAutoCancel(true)
            .build()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val NAME = "Reminder about purchase"
        private const val CHANNEL_ID = "cart_reminder_channel"
        private const val NOTIFICATION_ID = 1007
    }
}