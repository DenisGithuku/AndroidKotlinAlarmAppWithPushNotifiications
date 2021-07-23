package com.denisgithuku.notifications.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisgithuku.notifications.databinding.FragmentHomeBinding
import com.denisgithuku.notifications.utils.AlarmReceiver
import com.denisgithuku.notifications.viewmodels.MainViewModel
import com.denisgithuku.notifications.viewmodels.MainViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.util.*


private const val channelId = "testchannelid"
private const val channelName = "testchannelname"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewModelFactory = MainViewModelFactory(application)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        createNotificationsChannel(channelId, channelName)

        mainViewModel.input.observe(viewLifecycleOwner) {
            it.let {
                binding.timeDisplayText.text = it.toString()
            }
        }

        binding.timepickerButton.setOnClickListener {
            selectTime()
        }

        binding.notifier.setOnClickListener {
            Toast.makeText(requireContext(), "${mainViewModel.input.value}", Toast.LENGTH_SHORT).show()

            val context = requireContext()
            val alarmManager = requireContext().getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

            val seconds = 10000

            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + seconds,
                pendingIntent
            )
        }
        return binding.root
    }

    private fun selectTime() {
        val picker = MaterialTimePicker.Builder()
            .setHour(Date().hours)
            .setMinute(Date().minutes)
            .setTitleText("Select time")
            .setTimeFormat(CLOCK_12H)
            .build()

        picker.show(parentFragmentManager, "timealarmpicker")
    }

    private fun createNotificationsChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "denisgithukuappnotifications"
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}