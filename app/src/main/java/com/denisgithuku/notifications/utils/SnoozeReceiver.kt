package com.denisgithuku.notifications.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.navigation.NavDeepLinkBuilder
import com.denisgithuku.notifications.R

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        val pendingIntent = NavDeepLinkBuilder(context!!)
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.extrasFragment2)
//            .createPendingIntent()

        Log.d("snoozeReceiver", "Alarm snoozed")
    }

}