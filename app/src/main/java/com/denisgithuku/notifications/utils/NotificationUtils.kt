package com.denisgithuku.notifications.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.denisgithuku.notifications.R
import com.denisgithuku.notifications.ui.ExtrasFragment


abstract class NotificationUtils {
    companion object {

        val notificationId = 49876

        fun notificationUtils(context: Context, channelId: String) {
            val pendingIntent = NavDeepLinkBuilder(context)
                .setDestination(R.id.extrasFragment2)
                .setGraph(R.navigation.nav_graph)
                .createPendingIntent()
            val builder = NotificationCompat.Builder(
                context,
                channelId
            )
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Testing from the notification")
                .setContentTitle("Testing")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.notify(notificationId, builder.build())
        }
    }
}