package com.denisgithuku.notifications.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.denisgithuku.notifications.R

private const val NOTIFICATION_ID = 49876
private const val channelId = "testchannelid"

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingIntent = NavDeepLinkBuilder(context!!)
            .setDestination(R.id.extrasFragment2)
            .setGraph(R.navigation.nav_graph)
            .createPendingIntent()
        val builder = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Please wake up before I beat you up")
            .setContentTitle("Hallo")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}